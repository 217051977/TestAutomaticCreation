package logic.textStyle;

public class TextStyle {

    // Reset
    protected static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    protected static final String RED = "\033[0;31m";     // RED
    protected static final String GREEN = "\033[0;32m";   // GREEN
    protected static final String YELLOW = "\033[0;33m";  // YELLOW
    protected static final String BLUE = "\033[0;34m";    // BLUE
    protected static final String PURPLE = "\033[0;35m";  // PURPLE

    // Bold
    protected static final String RED_BOLD = "\033[1;31m";    // RED
    protected static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    protected static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    protected static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    protected static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE

    // Underline
    protected static final String UNDERLINED = "\033[4m";  // UNDERLINED
    protected static final String RED_UNDERLINED = "\033[4;31m";    // RED
    protected static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    protected static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    protected static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    protected static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE

    // Background
    protected static final String RED_BACKGROUND = "\033[41m";    // RED
    protected static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    protected static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    protected static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    protected static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE

    // High Intensity
    protected static final String RED_BRIGHT = "\033[0;91m";    // RED
    protected static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    protected static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    protected static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    protected static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE

    // Bold High Intensity
    protected static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    protected static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    protected static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    protected static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    protected static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE

    // High Intensity backgrounds
    protected static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    protected static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    protected static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    protected static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    protected static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE

    protected TextStyle() {}

}
