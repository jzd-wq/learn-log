# 2026-09-08 学习记录：使用 Flask 和 SQLite 实现运动注册系统

## 今日学习

- Flask 路由和请求处理
- GET 与 POST 请求
- HTML 表单提交
- Jinja2 模板渲染
- SQLite 数据库增删查操作
- 后端输入校验和参数化 SQL

## 今日实践

基于 Flask、Jinja2 和 SQLite 完成了一个学生运动注册系统，实现了：

- 用户填写姓名并选择运动项目
- 将报名信息保存到 SQLite 数据库
- 查看所有报名人员
- 取消报名
- 对空姓名、空运动项目和非法运动项目进行校验

## 关键代码

```python
@app.route('/register', methods=['POST'])
def register():
    name = request.form.get('name')
    sport = request.form.get('sport')

    if not name:
        return render_template('error.html', message='Missing your name.')

    if sport not in SPORTS:
        return render_template('error.html', message='Invalid sport selected.')

    db.execute(
        'INSERT INTO registrations (name, sport) VALUES (?, ?)',
        name,
        sport
    )

    return redirect('/registrants')
```

## 今日收获

理解了一个 Flask Web 应用从接收请求、校验数据、操作数据库到返回页面的完整流程。还认识到后端校验不能依赖前端，并学习了使用参数化 SQL 降低 SQL 注入风险。

## 面试可讲

这个项目可以重点介绍路由设计、表单处理、SQLite 数据库交互、后端数据校验、参数化 SQL，以及 Jinja2 模板继承和动态渲染。

## 后续计划

- 增加用户登录和会话功能
- 增加重复报名校验
- 为项目补充测试
- 优化页面样式和错误处理

## 项目地址

[CS50X--](https://github.com/jzd-wq/CS50X--)
