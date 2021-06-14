import java.util.Random;

/**
 * Throwing balls from a N-store Building
 *
 * Considering that the building has N floors and that the balls
 * that are thrown from a higher floor than F get broken, then a possible solution
 * to find F could be a liner search.
 * Going down from N, N-1, N-2.. until the ball doesn't get broken.
 * This will return a value for F, but in a linear search, and this doesn't meet
 * the lgN throws requirement.
 * So a possible option could be to find F floor with a BinarySearch (O lgN), that will speed
 * up the process.
 * For example:
 * 1- If throwing a ball from floor N gets broken, then try with N/2 until you find a floor where the ball
 * doesn't get broken. Once you find a floor where the ball doesn't get broken, then you
 * redefine the min and max range of floors to find the lower floor where the ball starts getting broken.
 *
 * 2- To find F-floor with O(2 lg F), a possible solution could be to start from floor 1, next 2, 4, 8 (2^i)
 * Once the ball gets broken (after lg F steps), a binary search could be done in the reduced range.
 * As the range < F, then the number of searches < log F.
 */

public class ThrowingBalls {

    private static int floorBroken;

    /**
     * n = number of floors of the building
     * floorBroken = number of the floor that defines if a ball will get broken or not
     */
    public static void main(String args[]){
        int n = 1000;
        Random rand = new Random();
        floorBroken = rand.nextInt(n);

        System.out.println("The random floor F is: " + floorBroken);
        System.out.println("Floor F found in lgN: " + findFloorF(n,0));
        System.out.println("Floor F found in 2lgF: " + findFloorFOtherWay());


    }

    /**
     * Function that returns if throwing a ball from a specific floor will get broken
     */
    public static boolean isBallBroken(int floor){
        return (floor >= floorBroken);
    }

    /**
     * Find floor F based on the N floors that has a building
     * Big O = lg N
     * Precondition: N >= F >= 1
     */
    public static int findFloorF(int maxFloor, int minFloor){
        //Precondition check
        if (maxFloor < minFloor){
            return -1;
        }
        //Precondition check
        if (maxFloor==0){
            return 0;
        }else if ((maxFloor == minFloor+1) && (!isBallBroken(maxFloor))){
            return minFloor;
        }else if ((maxFloor == minFloor+1) && (isBallBroken(maxFloor))) {
            return maxFloor;
        }else{
            if (isBallBroken(Math.floorDiv(maxFloor+minFloor,2))){
                return (findFloorF(Math.floorDiv(maxFloor+minFloor,2), minFloor));
            }else{
                return (findFloorF(maxFloor, Math.floorDiv(maxFloor+minFloor,2)));
            }
        }
    }

    /**
     * Starting the search from the bottom 1,2,4,8...until the first ball gets broken at 2^(k+1)
     * Then do a binary search between 2^k and 2^(k+1)
     * Big O = 2lg F (lg F steps of the exponential search + lg F of the binarySearch)
     * @return
     */
    public static int findFloorFOtherWay(){
        int i = 0;
        while (!isBallBroken(intPower(2,i))){
            i++;
        }
        if (i==0){
            return findFloorF(intPower(2,i), 0);
        }else{
            return findFloorF(intPower(2,i), intPower(2,i-1));
        }

    }

    private static int intPower(int x, int y){
        int result=1;
        for (int i=0; i<y; i++){
            result *=x;
        }
        return result;
    }

}
