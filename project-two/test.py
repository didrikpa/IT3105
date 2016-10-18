import gym
env = gym.make('Taxi-v1')
observation = env.reset()
print(env.action_space.n)

print(env.observation_space)

print(env.observation_space)

print observation

env.render()

