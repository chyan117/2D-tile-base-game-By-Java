package byow.Core;

public class save_signal {
    public static boolean save_flag;
    public static boolean flag(){
        return save_flag;
    }
    public static void SetTure(){
        save_flag = true;
    }
    public static void SetFalse(){
        save_flag = false;
    }
}
