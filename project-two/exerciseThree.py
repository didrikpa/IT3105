import gym
import numpy
import random

environment = gym.make('FrozenLake-v0')
qMatrix = numpy.zeros((environment.observation_space.n, environment.action_space.n))
qMatrix[qMatrix == 0] = 0.32
epsilon = 0.1



numberOfEpisodes = 3000

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
        qMatrix[state, action] += 0.1 * (reward + 0.99 * numpy.max(qMatrix[observation, :]) - qMatrix[state, action])
        episodeReward += reward
    if x > 1000:
        epsilon *= 0.999
print qMatrix


numberOfWins = 0
for x in range(1000):
    done = False
    observation = environment.reset()
    while not done:
        state = observation
        action = numpy.argmax(qMatrix[state, :])
        observation, reward, done, info = environment.step(action)
        if(observation == 15):
            numberOfWins += 1
            environment.render()
print numberOfWins

