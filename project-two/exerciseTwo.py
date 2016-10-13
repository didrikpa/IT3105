import gym
import numpy as np


def exerciseTwoA():
    environment = gym.make('FrozenLake-v0')
    environment.reset()

    qMatrix = np.zeros([environment.observation_space.n, environment.action_space.n])

    fixedQfunction = [0.5, 0.5, 1.0, 0.5]



