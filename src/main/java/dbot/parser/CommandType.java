package dbot.parser;

/**
 * Enum representing different command types that the parser can recognize.
 * Includes task commands (TODO, DEADLINE, EVENT) and action commands (LIST, MARK, UNMARK, DELETE, HELP, BYE, FIND).
 */
public enum CommandType {
    TODO, DEADLINE, EVENT, LIST, MARK, UNMARK, DELETE, HELP, BYE, UNKNOWN, FIND
}
