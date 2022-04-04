package com.sreekanth.final_assignment.part3.client;

public class ServerInitialization
{
    private static String clientMessage = "hello";
    public static void setClientMessage(String message)
    {
        clientMessage = message;
    }
    public static boolean waitForReceive;
    private static int car = -1; //Car allocated to this instance
    private static AppForm appForm; //Main form(AppForm containing game)

    public static String getClientMessage()
    {
        var output = clientMessage; //Retrieve message from memory
        clientMessage = ""; //clear client message
        return output; //Return
    }

    public static AppForm getForm()
    {
        if (appForm == null)
        {
            System.err.println("Assigned car: " + car);

            appForm = new AppForm(); //Initialize form
        }
        else return appForm;
        return null;
    }

    public static void setCar(int car)
    {
        if(ServerInitialization.car == -1)
            ServerInitialization.car = car;
    }

    public static int getCarIndex()
    {
        return car;
    }

}
