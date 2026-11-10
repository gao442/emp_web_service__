/**
 * 登录页逻辑
 */
async function doLogin() {
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const errorMsg = document.getElementById('errorMsg');
    errorMsg.textContent = '';

    if (!username || !password) {
        errorMsg.textContent = '用户名和密码不能为空';
        return;
    }

    try {
        const resp = await fetch('/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });
        const result = await resp.json();
        if (result.code === 1) {
            // 保存 JWT token
            localStorage.setItem('token', result.data);
            location.href = 'manage.html';
        } else {
            errorMsg.textContent = result.msg || '登录失败';
        }
    } catch (e) {
        errorMsg.textContent = '请求失败，请检查后端服务是否启动';
    }
}

// 回车键触发登录
document.addEventListener('keydown', e => {
    if (e.key === 'Enter') doLogin();
});
