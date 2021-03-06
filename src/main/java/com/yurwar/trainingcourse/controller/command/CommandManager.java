package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.ActivityRequestService;
import com.yurwar.trainingcourse.model.service.ActivityService;
import com.yurwar.trainingcourse.model.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * Singletone class, provides map of all commands and inject necessary service to their constructor
 *
 * @author Yurii Matora
 */
public class CommandManager {
    /**
     * Singletone instance of class
     */
    private static CommandManager commandManager;
    /**
     * Map of commands
     *
     * @see Command
     */
    private final Map<String, Command> commandMap = new HashMap<>();

    /**
     * Initialize command map with all paths to commands and necessary services
     */
    private CommandManager() {
        final UserService userService = new UserService();
        final ActivityService activityService = new ActivityService();
        final ActivityRequestService activityRequestService = new ActivityRequestService();
        commandMap.put("/login", new LoginCommand(userService));
        commandMap.put("/logout", new LogoutCommand());
        commandMap.put("/registration", new RegistrationCommand(userService));
        commandMap.put("/users", new UsersCommand(userService));
        commandMap.put("/index", new HomeCommand());
        commandMap.put("/users/delete", new UserDeleteCommand(userService));
        commandMap.put("/users/update", new UserUpdateCommand(userService));
        commandMap.put("/profile", new UserProfileCommand(userService));
        commandMap.put("/profile/update", new UserProfileUpdateCommand(userService));
        commandMap.put("/activities", new ActivitiesCommand(activityService));
        commandMap.put("/activities/add", new ActivityAddCommand(activityService));
        commandMap.put("/activities/request", new ActivityRequestsCommand(activityRequestService));
        commandMap.put("/activities/delete", new ActivityDeleteCommand(activityService));
        commandMap.put("/activities/mark-time", new MarkTimeCommand(activityService));
        commandMap.put("/activities/request/approve", new ActivityRequestApproveCommand(activityRequestService));
        commandMap.put("/activities/request/reject", new ActivityRequestRejectCommand(activityRequestService));
        commandMap.put("/activities/request/add", new ActivityRequestAddCommand(activityRequestService));
        commandMap.put("/activities/request/complete", new ActivityRequestCompleteCommand(activityRequestService));
    }

    /**
     * @return the only instance of class
     */
    public static CommandManager getInstance() {
        if (commandManager == null) {
            synchronized (CommandManager.class) {
                if (commandManager == null) {
                    commandManager = new CommandManager();
                }
            }
        }
        return commandManager;
    }

    /**
     * @param commandName path in command line
     * @return command that can handle user request with execute method
     */
    public Command getCommand(String commandName) {
        return commandMap.getOrDefault(commandName, r -> "/index.jsp");
    }
}
