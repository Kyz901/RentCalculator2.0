package RentCalculator.model;

public final class CurrentUser {

    private CurrentUser() {
        //avoid to create instance outside
    }
    private static User user;

    public static User get() {
        return user;
    }

    public static void set(User user) {
        CurrentUser.user = user;
    }
}
