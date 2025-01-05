/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount; 
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        if (name == null) {
            return null; 
        }
        
        for (int i = 0; i < userCount; i++) { 
            User currentUser = this.users[i];
            
            if (currentUser != null && currentUser.getName().equals(name)) {
                return currentUser; 
            }
        }
        
        return null; 
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (this.userCount == users.length) return false; 
        for (int i = 0; i < userCount; i++) {
            if(this.users[i].getName().equals(name)) return false; 
        }
        User a = new User(name); 
        this.users[userCount] = a; 
        userCount++; 
        return true; 
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        boolean flag1 = false; 
        boolean flag2 = false; 
        int index = -1;

        for (int i = 0; i < userCount; i++) { 
            if (this.users[i].getName().equals(name1)) { 
                flag1 = true;
                index = i;
            }  
            if (this.users[i].getName().equals(name2)) flag2 = true; 
        }
        if (flag1 && flag2) { 
            users[index].addFollowee(name2); 
            return true;
        } 
        return false;
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User mostRecommendedUserToFollow = null; 
        int bestOneyet = -1; 
        User user1 = null; 
        for(int k = 0; k < userCount; k++) { 
            if (users[k].getName().equals(name)) { 
                user1 = users[k]; 
                break; 
            }
        }
        for(int i = 0; i < userCount; i++) { 
            if (users[i].getName().equals(name)) continue; 
            if (user1.countMutual(users[i]) > bestOneyet) { 
                mostRecommendedUserToFollow = users[i]; 
                bestOneyet = user1.countMutual(users[i]); 
            }
        }
        return mostRecommendedUserToFollow.getName();
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        if (userCount == 0) return null; 
        int currentBest = 0; 
        User targetedUser = null; 
        for (int i = 0; i < userCount; i++) { 
            if (followeeCount(users[i].getName()) > currentBest ) {
                targetedUser = users[i]; 
                currentBest = followeeCount(users[i].getName());
            }
        }
        return targetedUser != null ? targetedUser.getName() : null;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int sum = 0; 
        for (int i = 0; i < userCount; i++) { 
            if(users[i].follows(name)) sum++; 
        }
        return sum;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String ans = "Network:" + "\n"; 
       for ( int i = 0; i < this.userCount - 1; i++) { 
        ans = ans + users[i].toString() + "\n"; 
       }
       ans = ans + users[this.userCount - 1].toString();
       return ans;
    }
}
