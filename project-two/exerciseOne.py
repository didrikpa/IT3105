import gym

def exerciseOneFrozenLake():
    environment = gym.make('FrozenLake-v0')

    observation = environment.reset()

    done = False

    while not done:
        action = environment.action_space.sample()
        observation, reward, done, info = environment.step(action)
        environment.render()


def exerciseOneTaxi():
    environment = gym.make('Taxi-v1')

    observation = environment.reset()

    done = False

    while not done:
        action = environment.action_space.sample()
        observation, reward, done, info = environment.step(action)
        environment.render()

