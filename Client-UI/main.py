from flask import Flask, render_template, flash
from flask_bootstrap import Bootstrap
from flask_nav.elements import *
from flask_nav import Nav
from controller import *

nav = Nav()

app = Flask(__name__)
Bootstrap(app)
nav.init_app(app)


@nav.navigation()
def mynavbar():
    return Navbar(
        'Client-UI',
        View('Home', 'index'),
        View('Top 100', 'top')
    )


@app.route('/', methods=['POST', 'GET'])
def index():
    if request.method == 'POST':
        user_input = request.form['search']
        personas = search_for_persona(user_input)
        if len(personas) is 0:
            flash("%s not found" % (user_input))
            return render_template('index.html')
        return select_persona(personas)
    return render_template('index.html')


@app.route('/top', methods=['GET'])
def top():
    return render_template('top.html')


def select_persona(result):
    return render_template('select_persona.html', result=result)

@app.route('/display_persona')
def display_persona():
    id = request.args.get('id')
    persona = get_persona(id)
    return render_template('persona.html', persona=persona)

@app.route('/test')
def temp():
    return 'Hello World!'

@app.route('/show_tweets')
def show_tweets():
    id = request.args.get('id')
    tweets = get_tweets(id)
    return render_template('tweets.html', tweets=tweets)

if __name__ == "__main__":
    app.secret_key = "this_is_a_secret"
    app.run()
