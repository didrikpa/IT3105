import gym
import numpy
import random

environment = gym.make('FrozenLake-v0')
fixedQFunctionForPartB = [0.5, 1, 0.5, 0.5]


def exerciseTwoPartA():
    done = False
    while not done:
        action = fixedQFunctionForPartA()
        observation, reward, done, info = environment.step(action)
        environment.render()


def fixedQFunctionForPartA():
    randomNumber = random.random()
    if (randomNumber < 0.4):
        action = 1
    elif (randomNumber < 0.6):
        action = 0
    elif (randomNumber < 0.8):
        action = 2
    elif (randomNumber < 1):
        action = 3
    return action


def exerciseTwoPartB():
    done = False
    while not done:
        action = numpy.argmax(fixedQFunctionForPartB[:])
        observation, reward, done, info = environment.step(action)
        environment.render()


def exerciseTwoPartC():
    done = False
    epsilon = 0.1
    while not done:
        randomNumber = random.random()
        if(randomNumber < epsilon):
            action = environment.action_space.sample()
        else:
            action = numpy.argmax(fixedQFunctionForPartB[:])
        observation, reward, done, info = environment.step(action)
        print observation
        environment.render()


def learning(environment, qMatrix, learningRange, part):
    for x in range(learningRange):
        observation = environment.reset()
        done = False

        while not done:
            state = observation
            if (part == "A"):
                action = environment.action_space.sample()
            elif (part == "B"):
                action = numpy.argmax(qMatrix[state, :])
            elif (part == "C"):
                randomNumb = random.random()
                epsilon = 0.2
                if (randomNumb < epsilon):
                    action = environment.action_space.sample()
                else:
                    action = numpy.argmax(qMatrix[state, :])
            print action
            observation, reward, done, info = environment.step(action)
            environment.render()
            qMatrix[state, action] = qFunction(action)



exerciseTwoPartC()
