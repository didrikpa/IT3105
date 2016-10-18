import gym
import numpy
import random
import matplotlib.pyplot as plt

environment = gym.make('Taxi-v1')
qMatrix = numpy.zeros((environment.observation_space.n, environment.action_space.n))
qMatrix[qMatrix == 0] = 1
epsilon = 0.1



numberOfEpisodes = 3000
rewards = numpy.zeros((numberOfEpisodes))


for x in range(numberOfEpisodes):
    print x
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
        qMatrix[state, action] = qMatrix[state, action] + 0.8*(reward + 0.99 * numpy.max(qMatrix[observation, :]) - qMatrix[state, action])
        episodeReward += reward
        #print episodeReward
    rewards[x] = episodeReward
    epsilon *= 0.85

plt.plot(rewards)
plt.ylabel('Rewards')
plt.grid()
plt.show()

print qMatrix

numberOfWins = 0
for x in range(1000):
    done = False
    observation = environment.reset()
    while not done:
        state = observation
        action = numpy.argmax(qMatrix[state, :])
        observation, reward, done, info = environment.step(action)
        if(reward == 20):
            numberOfWins += 1
            environment.render()
            print numberOfWins


