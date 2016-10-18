import gym
import numpy
import random

environment = gym.make('Taxi-v1')
qMatrix = numpy.zeros((environment.observation_space.n, environment.action_space.n))
qMatrix[qMatrix == 0] = 0.3
epsilon = 0.9



numberOfEpisodes = 3

for x in range(numberOfEpisodes):
    observation = environment.reset()
    done = False
    episodeReward = 0
    while not done:
        state = observation
        randomNumber = random.random()
        if(randomNumber < epsilon):
            action = environment.action_space.sample()
        else:
            action = numpy.argmax(qMatrix[state, :])
        observation, reward, done, info = environment.step(action)
        qMatrix[state, action] = qMatrix[state, action] + 0.1*(reward + 0.99 * numpy.argmax(qMatrix[observation, :]) - qMatrix[state, action])
        episodeReward += reward
        #environment.render()
        print qMatrix#, reward, 0.99 * numpy.argmax(qMatrix[observation, :])
    if(x > 1000):
        epsilon *= 0.999
    print qMatrix, x, epsilon
environment.render()
numberOfWins = 0
for x in range(1000):
    done = False
    observation = environment.reset()
    while not done:
        #environment.render()
        state = observation
        action = numpy.argmax(qMatrix[state, :])
        observation, reward, done, info = environment.step(action)
        if(observation == 15):
            numberOfWins += 1
            environment.render()
print numberOfWins


