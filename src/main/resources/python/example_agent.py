from com.gdmc.api import BasicAgentController, AgentControllerRegister

###################################################################################################
# To the users: Do not modify this file, it will be overridden on startup! Make a copy instead :) #
# You can modify this behavior by editing the configuration files in the plugin folder .          #
# Note that we are using python 2.7 since Jython doesn't support python 3 atm... T-T              #
# If, like me, you dislike python 2, you should try creating your agent with Java & Spigot API    #
###################################################################################################


class ExampleAgent(BasicAgentController):
    """
    An example agent. Forgive me for not following PEP8 for function names, we are overriding java functions ;)
    """
    
    def __init__(self, name=None):
        BasicAgentController.__init__(self)
        self.agent_name = name

    def onTick(self):
        """
        Called every tick
        """
        return 0

    def onSpawn(self):
        """
        Called just after the NPC is spawned.
        """
        self.say('Agent {} spawned!'.format(self.agent_name))
        self.say('Available functions: [{}]'.format(", ".join([f for f in dir(self.__class__) if not f.startswith("__")])))

    def onDespawn(self):
        """
        Called just before the NPC entity is despawned.
        """
        self.say('Agent {} de-spawned !'.format(self.agent_name))


AgentControllerRegister.getInstance().register(ExampleAgent)
