# 使用 Flask 和 SQLite 实现学生运动注册系统

> 日期：2026 年 9 月 8 日

项目地址：[CS50X--](https://github.com/jzd-wq/CS50X--)

## 一、项目简介

这是一个基于 Flask 的运动项目报名系统，主要实现以下功能：

- 展示运动报名页面
- 填写姓名并选择运动项目
- 将报名信息保存到 SQLite 数据库
- 查看所有已报名人员
- 取消报名
- 对缺少信息或非法运动项目进行错误提示

项目支持 Basketball、Soccer 和 Ultimate Frisbee 三种运动项目。

## 二、项目结构

```text
CS50X--
├── app.py
├── registrations.db
└── templates
    ├── layout.html
    ├── index.html
    ├── registrants.html
    ├── error.html
    ├── failure.html
    └── success.html
```

其中，`app.py` 负责后端路由和业务逻辑，`templates` 目录负责页面展示，`registrations.db` 用于保存报名数据。

## 三、初始化 Flask 和数据库

```python
from cs50 import SQL
from flask import Flask, render_template, request, redirect

app = Flask(__name__)
db = SQL('sqlite:///registrations.db')
```

SQLite 不需要单独启动数据库服务，适合学习项目和小型应用。通过 `db.execute()` 可以执行数据库查询、插入和删除操作。

## 四、使用路由返回页面

```python
SPORTS = ['Basketball', 'Soccer', 'Ultimate Frisbee']

@app.route('/')
def index():
    return render_template('index.html', SPORTS=SPORTS)
```

访问根路径时，Flask 会渲染 `index.html`，并把运动项目列表传递给模板。Jinja2 再通过循环动态生成下拉选项：

```html
{% for sport in SPORTS %}
  <option value="{{ sport }}">{{ sport }}</option>
{% endfor %}
```

## 五、处理表单提交和数据校验

报名表单通过 POST 请求提交：

```python
@app.route('/register', methods=['POST'])
def register():
    name = request.form.get('name')
    sport = request.form.get('sport')

    if not name:
        return render_template('error.html', message='Missing your name.')

    if not sport:
        return render_template('error.html', message='Missing your sport.')

    if sport not in SPORTS:
        return render_template('error.html', message='Invalid sport selected.')
```

这里进行了三层校验：

1. 姓名不能为空；
2. 必须选择运动项目；
3. 运动项目必须属于后端允许的列表。

前端页面只能改善用户体验，不能代替后端校验，因为用户仍然可以手动构造 HTTP 请求。

## 六、将数据写入 SQLite

```python
db.execute(
    'INSERT INTO registrations (name, sport) VALUES (?, ?)',
    name,
    sport
)

return redirect('/registrants')
```

这里使用 `?` 作为参数占位符，而不是拼接字符串。参数化 SQL 可以降低 SQL 注入风险，也是更规范的数据库操作方式。

## 七、查询和展示报名信息

```python
@app.route('/registrants')
def registrants():
    registrants = db.execute('SELECT * FROM registrations')
    return render_template('registrants.html', registrants=registrants)
```

模板使用 Jinja2 循环展示查询结果：

```html
{% for registrant in registrants %}
  <tr>
    <td>{{ registrant['name'] }}</td>
    <td>{{ registrant['sport'] }}</td>
  </tr>
{% endfor %}
```

## 八、实现取消报名

前端通过隐藏字段传递报名记录的 ID：

```html
<form action="/deregister" method="post">
  <input name="id" type="hidden" value="{{ registrant['id'] }}">
  <button type="submit">Deregister</button>
</form>
```

后端根据 ID 删除对应记录：

```python
@app.route('/deregister', methods=['POST'])
def deregister():
    id = request.form.get('id')

    if id:
        db.execute('DELETE FROM registrations WHERE id=?', id)

    return redirect('/registrants')
```

## 九、使用模板继承

公共页面结构写在 `layout.html` 中：

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>CS50X</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
  </head>
  <body>
    {% block body %}{% endblock %}
  </body>
</html>
```

其他页面通过 `extends` 继承公共布局：

```html
{% extends 'layout.html' %}

{% block body %}
  <h1>Register</h1>
{% endblock %}
```

这样可以减少重复代码，也方便统一修改页面标题、字符集和公共样式。

## 十、项目运行流程

```text
访问首页
  ↓
填写姓名并选择运动项目
  ↓
提交 POST /register
  ↓
后端校验表单
  ↓
写入 registrations.db
  ↓
跳转到 /registrants
  ↓
查看或取消报名
```

## 十一、面试中可以怎么介绍

这个项目使用 Flask、Jinja2 和 SQLite 实现了一个运动报名系统，完成了报名、查询和取消报名等功能。

面试时可以重点介绍：

- 使用 Flask 路由处理不同业务请求，并区分 GET 和 POST 方法；
- 使用 Jinja2 模板动态渲染运动选项和报名列表；
- 使用 SQLite 实现数据新增、查询和删除；
- 对用户输入进行后端校验，保证数据有效性；
- 使用参数化 SQL 降低 SQL 注入风险；
- 使用模板继承复用公共 HTML 结构。

可以将项目总结为：

> 这个项目让我完整实践了一个 Flask Web 应用从接收请求、校验数据、操作数据库到返回页面的完整流程，也加深了我对路由设计、表单处理、数据库交互和后端安全校验的理解。

## 十二、后续改进方向

- 增加重复报名校验；
- 增加用户登录和会话功能；
- 增加 CSRF 防护；
- 优化页面样式和错误提示；
- 为报名、查询和删除功能增加自动化测试。

## 总结

这个项目规模虽然不大，但完整覆盖了 Flask Web 开发的基础流程：路由、表单、模板、数据库和输入校验。通过把一个简单的报名需求实现为完整的 Web 应用，我进一步理解了浏览器、服务器和数据库之间的协作方式，也为后续学习用户认证、REST API、ORM 和前后端分离打下了基础。
