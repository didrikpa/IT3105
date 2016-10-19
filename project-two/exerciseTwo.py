import gym
import numpy
import random

environment = gym.make('FrozenLake-v0')
fixedQFunction = [0.5, 1, 0.5, 0.5]



def exerciseTwoPartB():
    done = False
    while not done:
        action = numpy.argmax(fixedQFunction[:])
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
            action = numpy.argmax(fixedQFunction[:])
        observation, reward, done, info = environment.step(action)
        environment.render()


exerciseTwoPartC()