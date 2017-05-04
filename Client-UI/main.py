from flask import Flask, render_template
from flask_bootstrap import Bootstrap
from flask_nav.elements import *
from flask_nav import Nav

nav = Nav()

app = Flask(__name__)
Bootstrap(app)
nav.init_app(app)


@nav.navigation()
def mynavbar():
    return Navbar(
        'Client-UI',
        View('Home', 'index'),

    )


@app.route('/', methods=['POST', 'GET'])
def index():
    if request.method == 'POST':
        pass
    return render_template('index.html')


@app.route('/test')
def temp():
    return 'Hello World!'


if __name__ == "__main__":
    app.run()
