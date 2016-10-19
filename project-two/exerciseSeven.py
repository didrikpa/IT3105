import random

import gym
import matplotlib.pyplot as plotter
import numpy

environment = gym.make('Taxi-v1')
qMatrix = numpy.zeros((environment.observation_space.n, environment.action_space.n))
qMatrix[qMatrix == 0] = 1
epsilon = 0.1

numberOfEpisodes = 3000
rewardsForPlotting = []

for x in range(numberOfEpisodes):
    observation = environment.reset()
    done = False
    episodeReward = 0
    while not done:
        state = observation
        randomNumber = random.random()
        if randomNumber < epsilon:
            action = environment.action_space.sample()
        else:
            action = numpy.argmax(qMatrix[state, :])
        observation, reward, done, info = environment.step(action)
        qMatrix[state, action] += 0.8 * (
            reward + 0.99 * numpy.max(qMatrix[observation, :]) - qMatrix[state, action])
        episodeReward += reward
    rewardsForPlotting.append(episodeReward)
    epsilon *= 0.85

plotter.plot(rewardsForPlotting)
plotter.ylabel("Rewards")
plotter.xlabel("Episodes")
plotter.grid()
plotter.show()

print qMatrix

numberOfWins = 0
for x in range(1000):
    done = False
    observation = environment.reset()
    while not done:
        state = observation
        action = numpy.argmax(qMatrix[state, :])
        observation, reward, done, info = environment.step(action)
        if reward == 20:
            numberOfWins += 1
            environment.render()
            print numberOfWins
