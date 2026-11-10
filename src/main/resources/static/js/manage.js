/**
 * 管理页逻辑：部门管理 + 员工管理
 * 接口约定：
 *   GET    /depts            部门列表
 *   POST   /depts            新增部门 {name}
 *   PUT    /depts            修改部门 {id, name}
 *   DELETE /depts/{id}       删除部门
 *   GET    /emps?page=&pageSize=          员工分页
 *   POST   /emps/limit       条件分页 {name,gender,entrydate,page,pageSize}
 *   POST   /emps             新增员工
 *   PUT    /emps             修改员工
 *   DELETE /emps/{ids}       删除员工（支持逗号分隔批量）
 *   POST   /upload           图片上传（form-data，字段名 image）→ data 为图片 URL
 * 分页返回结构 PageBean：{ empList: [...], total: n }
 */

const state = { page: 1, pageSize: 10, total: 0, depts: [] };

/* ==================== Tab 切换 ==================== */
function switchTab(tab) {
    document.getElementById('tabEmp').className = 'tab' + (tab === 'emp' ? ' active' : '');
    document.getElementById('tabDept').className = 'tab' + (tab === 'dept' ? ' active' : '');
    document.getElementById('empPanel').classList.toggle('hidden', tab !== 'emp');
    document.getElementById('deptPanel').classList.toggle('hidden', tab !== 'dept');
    if (tab === 'dept') loadDepts(); else initEmp();
}

/* ==================== 部门管理 ==================== */
async function loadDepts() {
    const result = await request('/depts');
    state.depts = result.data || [];
    document.getElementById('deptBody').innerHTML = state.depts.map(d => `
        <tr>
            <td>${d.id}</td>
            <td>${d.name}</td>
            <td>${d.createTime || '-'}</td>
            <td>
                <button class="small" onclick='openDeptModal(${JSON.stringify(d)})'>编辑</button>
                <button class="small danger" onclick="deleteDept(${d.id})">删除</button>
            </td>
        </tr>`).join('');
}

// 加载部门下拉框（员工表单用）
async function loadDeptOptions() {
    if (!state.depts.length) {
        const result = await request('/depts');
        state.depts = result.data || [];
    }
    document.getElementById('eDept').innerHTML =
        state.depts.map(d => `<option value="${d.id}">${d.name}</option>`).join('');
}

function openDeptModal(dept) {
    document.getElementById('deptModalTitle').textContent = dept ? '编辑部门' : '新增部门';
    document.getElementById('dId').value = dept ? dept.id : '';
    document.getElementById('dName').value = dept ? dept.name : '';
    showModal('deptMask');
}

async function saveDept() {
    const id = document.getElementById('dId').value;
    const name = document.getElementById('dName').value.trim();
    if (!name) { alert('部门名称不能为空'); return; }
    const result = id
        ? await request('/depts', { method: 'PUT', body: JSON.stringify({ id: +id, name }) })
        : await request('/depts', { method: 'POST', body: JSON.stringify({ name }) });
    if (result.code === 1) { closeModal('deptMask'); loadDepts(); } else alert(result.msg);
}

async function deleteDept(id) {
    if (!confirm('确定删除该部门？（部门下若存在员工将删除失败）')) return;
    const result = await request('/depts/' + id, { method: 'DELETE' });
    if (result.code === 1) loadDepts(); else alert(result.msg);
}

/* ==================== 员工管理 ==================== */
async function initEmp() {
    await loadDeptOptions();
    searchEmp(1);
}

async function searchEmp(page) {
    if (page < 1) page = 1;
    state.page = page;
    const params = {
        name: document.getElementById('qName').value.trim() || null,
        gender: document.getElementById('qGender').value ? +document.getElementById('qGender').value : null,
        entrydate: document.getElementById('qEntrydate').value || null,
        page: state.page,
        pageSize: state.pageSize
    };
    const result = await request('/emps/limit', { method: 'POST', body: JSON.stringify(params) });
    const data = result.data || { empList: [], total: 0 };
    state.total = data.total || 0;

    const deptMap = {};
    state.depts.forEach(d => deptMap[d.id] = d.name);

    document.getElementById('empBody').innerHTML = (data.empList || []).map(e => `
        <tr>
            <td><input type="checkbox" class="empCheck" value="${e.id}"></td>
            <td>${e.image ? `<img class="avatar" src="${e.image}">` : '<span class="avatar"></span>'}</td>
            <td>${e.name}</td>
            <td>${e.username}</td>
            <td>${e.gender === 1 ? '男' : '女'}</td>
            <td>${e.job}</td>
            <td>${(e.entrydate || '').substring(0, 10)}</td>
            <td>${deptMap[e.deptId] || e.deptId || '-'}</td>
            <td>
                <button class="small" onclick='openEmpModal(${JSON.stringify(e)})'>编辑</button>
                <button class="small danger" onclick="deleteEmps([${e.id}])">删除</button>
            </td>
        </tr>`).join('');
    document.getElementById('checkAll').checked = false;
    renderPageInfo();
}

function renderPageInfo() {
    const pages = Math.ceil(state.total / state.pageSize) || 1;
    document.getElementById('pageInfo').textContent =
        `共 ${state.total} 条，第 ${state.page} / ${pages} 页`;
}

function toggleAll(cb) {
    document.querySelectorAll('.empCheck').forEach(c => c.checked = cb.checked);
}

function openEmpModal(emp) {
    document.getElementById('empModalTitle').textContent = emp ? '编辑员工' : '新增员工';
    document.getElementById('eId').value = emp ? emp.id : '';
    document.getElementById('eUsername').value = emp ? emp.username : '';
    document.getElementById('ePassword').value = '';
    document.getElementById('eName').value = emp ? emp.name : '';
    document.getElementById('eGender').value = emp ? emp.gender : 1;
    document.getElementById('eJob').value = emp ? emp.job : 1;
    document.getElementById('eEntrydate').value = emp ? (emp.entrydate || '').substring(0, 10) : '';
    document.getElementById('eImage').value = emp ? (emp.image || '') : '';
    document.getElementById('eImagePreview').src = emp ? (emp.image || '') : '';
    if (emp) document.getElementById('eDept').value = emp.deptId;
    showModal('empMask');
}

// 头像上传：选择文件后立即上传到 OSS，回填 URL
async function uploadImage(input) {
    const file = input.files[0];
    if (!file) return;
    const fd = new FormData();
    fd.append('image', file);
    try {
        const result = await request('/upload', { method: 'POST', body: fd });
        if (result.code === 1) {
            document.getElementById('eImage').value = result.data;
            document.getElementById('eImagePreview').src = result.data;
        } else {
            alert('上传失败：' + result.msg);
        }
    } catch (e) {
        alert('上传异常：' + e.message);
    }
}

async function saveEmp() {
    const id = document.getElementById('eId').value;
    const emp = {
        username: document.getElementById('eUsername').value.trim(),
        name: document.getElementById('eName').value.trim(),
        gender: +document.getElementById('eGender').value,
        job: +document.getElementById('eJob').value,
        entrydate: document.getElementById('eEntrydate').value || null,
        deptId: +document.getElementById('eDept').value,
        image: document.getElementById('eImage').value || null
    };
    const password = document.getElementById('ePassword').value.trim();
    if (password) emp.password = password;

    if (!emp.username || !emp.name) { alert('用户名和姓名不能为空'); return; }

    let result;
    if (id) {
        emp.id = +id;
        result = await request('/emps', { method: 'PUT', body: JSON.stringify(emp) });
    } else {
        if (!password) { alert('新增员工密码不能为空'); return; }
        result = await request('/emps', { method: 'POST', body: JSON.stringify(emp) });
    }
    if (result.code === 1) { closeModal('empMask'); searchEmp(state.page); } else alert(result.msg);
}

async function deleteEmps(ids) {
    if (!confirm(`确定删除选中的 ${ids.length} 名员工？`)) return;
    const result = await request('/emps/' + ids.join(','), { method: 'DELETE' });
    if (result.code === 1) searchEmp(state.page); else alert(result.msg);
}

function batchDelete() {
    const ids = [...document.querySelectorAll('.empCheck:checked')].map(c => +c.value);
    if (!ids.length) { alert('请先勾选要删除的员工'); return; }
    deleteEmps(ids);
}

// 页面初始化
initEmp();
