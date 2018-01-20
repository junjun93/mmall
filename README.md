好
                     dao                   service         controller
登录功能    checkUsername selectLogin          login            login
登出

注：适应ServerResponse可复用的服务响应类、Const常量类的代码富用方式


登出logout(session)
注册register 调用接口checkEmail  ctrl+t
枚举分组、内部接口分组 Role() ROLE_ADMIN ROLE_CUSTOMER
校验 checkValid(str,type) EMAIL USERNAME register复用代码

4.getUserInfo(session)
forgetGetQuesion selectQuestion selectQuestionByUsername-d
forgetCheckAnswer(username.question,answer)
checkAnswer() forgettOKEN  TokenCatch
    
5.forgetRestPassword() forgetResetPassword()
 isBlank校验forgetToken  username是否存在
_token TOKEN_PREFIX token过期校验、token equal
 updatPasswordByname() 