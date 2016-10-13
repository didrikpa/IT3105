import gym

def checker():
    # env = gym.make('FrozenLake-v0')
    env = gym.make('Taxi-v1')
    for _ in range(10):
        env.reset()
        oldObservation = 0
        oldDone = 0
        oldInfo = 0
        oldReward = 0

        for t in range(50):
            env.render()
            action = env.action_space.sample()
            observation, reward, done, info = env.step(action)

            print "Observation: ", observation, ", reward: ", reward, ", done: ", done, ", info: ", info
            print "Observation: ", oldObservation, ", reward: ", oldReward, ", done: ", oldDone, ", info: ", oldInfo
            oldObservation = observation
            oldReward = reward
            oldDone = done
            oldInfo = info

            if done:
                if reward == 20:
                    print "hei"
                    env.render()
                print("Episode finished after {} timesteps".format(t + 1))
                break





def exerciseTwoA():
