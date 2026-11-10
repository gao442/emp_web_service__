/**
 * 公共工具：token 管理、请求封装、登录状态检查
 */

// 退出登录：清除 token 并跳转登录页
function logout() {
    localStorage.removeItem('token');
    location.href = 'login.html';
}

// 页面加载时检查登录状态（登录页除外）
if (!localStorage.getItem('token') && !location.pathname.includes('login')) {
    logout();
}

/**
 * 通用请求封装
 * - 自动携带 token 请求头
 * - 自动识别 NOT_LOGIN 并跳转登录页
 * @param {string} url 请求地址
 * @param {object} options fetch 配置（method/body/headers）
 * @returns {Promise<object>} 后端返回的 Result 对象 {code, msg, data}
 */
async function request(url, options = {}) {
    // JSON 请求默认设置 Content-Type（FormData 上传不能设置）
    if (!(options.body instanceof FormData)) {
        options.headers = Object.assign({ 'Content-Type': 'application/json' }, options.headers || {});
    } else {
        options.headers = Object.assign({}, options.headers || {});
    }
    const token = localStorage.getItem('token');
    if (token) options.headers['token'] = token;

    const resp = await fetch(url, options);
    const result = await resp.json();
    // 登录失效统一处理
    if (result.code === 0 && result.msg === 'NOT_LOGIN') {
        alert('登录已过期，请重新登录');
        logout();
        throw new Error('NOT_LOGIN');
    }
    return result;
}

// 弹窗开关
function showModal(id) { document.getElementById(id).style.display = 'flex'; }
function closeModal(id) { document.getElementById(id).style.display = 'none'; }
