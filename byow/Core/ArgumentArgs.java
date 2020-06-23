package byow.Core;
//Whether the mode is interactive or not??
public class ArgumentArgs {
    private static boolean Interact_or_not;
    public static void SetTure(){
        Interact_or_not = true;
    }
    public static void SetFalse(){
        Interact_or_not = false;
    }
    public static boolean Mode(){
        return Interact_or_not;
    }
}
