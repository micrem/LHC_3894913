import java.util.ArrayList;
import java.util.Date;

public class IDCard {
    private Person person;
    private Chip chip;

    private String id;
    private Date validFrom;
    private Date validTo;
    private int[][] irisStructure = new int[10][10];
    private ArrayList<Permission> permissionList;
    private boolean isLocked;
}
