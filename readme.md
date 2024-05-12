dsy-laboratory是基于vue2的前端工程文件

    src-assets存放静态资源文件    
    router下的index.js是路由配置文件要加新的页面就创建vue组件在这里添加路由
    utils下的request.js是axios请求封装文件，这里封装的是我主机的后端springboot项目，向老师给的接口发送的请求没有封装
    views下的文件是页面文件，login是登录页面，register是注册页面，Manage是主页面，manage文件夹中为系统中主页面的子页面。

springboot是后端文件，目前只有一个user类及其的增删改查，controller接受请求，响应数据，然后调用service进行逻辑处理，在调用mapper进行数据库操作。mapper基于mybatis。

exception封装了一部分异常，common中，CorsConfig是跨域配置，这个文件使90端口可以被别的端口访问，InterceptorConfig是拦截器配置，这个文件使得请求在进入controller之前进行拦截，判断是否登录，如果没有登录则返回未登录的信息。
JwtInterceptor是拦截器的具体实现，JwtToken是token的生成和解析，这个文件是用来生成token的，Result是封装的返回结果。

如果要在后台新建实体类的话，在entity创建对应的实体类，再编写对应的Controller，service和mapper。

application.yml用来配置端口号和连接我本机的数据库