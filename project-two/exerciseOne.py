import gym

def exerciseOneFrozenLake():
    environment = gym.make('FrozenLake-v0')

    environment.reset()

    done = False

    while not done:
        action = environment.action_space.sample()
        observation, reward, done, info = environment.step(action)
        environment.render()
        print action


def exerciseOneTaxi():
    env = gym.make('Taxi-v1')

    env.reset()

    done = False

    while not done:
        action = env.action_space.sample()
        observation, reward, done, info = env.step(action)
        env.render()

exerciseOneFrozenLake()