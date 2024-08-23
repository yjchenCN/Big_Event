import request from '@/utils/request.js'

//提供调用注册接口的函数
export const userRegisterService = (registerData) => {  //处理用户填写的数据
    //将用户填写的数据转换成 URL 查询字符串参数的形式。
    const params = new URLSearchParams()
    for (const key in registerData) {
        params.append(key, registerData[key])  //key为registerData的键，也就是属性
    }
    return request.post('/user/register', params);  //将后台返回的结果返回给调用者
}

//提供调用登陆接口的函数
export const userLoginService = (loginData) => {
    const params = new URLSearchParams()
    for (const key in loginData) {
        params.append(key, loginData[key])
    }
    return request.post('/user/login', params)
}

//获取用户详细信息
export const userInfoService = () => {
    return request.get('/user/userInfo')
}

//修改个人信息
export const userUpdateService = (userData) => {
    return request.put('/user/update', userData)
}