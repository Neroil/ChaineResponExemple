package org.example.hihi;

import java.util.ArrayList;
import java.util.List;

public class main2 {
    public static void main(String[] args) {
        // Set up the chain of responsibility
        AttackHandler physicalAttackHandler = new PhysicalAttackHandler();
        AttackHandler rangeCheckerHandler   = new RangeCheckerHandler();
        AttackHandler manaCheckerHandler    = new ManaCheckerHandler();
        AttackHandler staminaCheckerHandler = new StaminaCheckerHandler();
        AttackHandler magicalAttackHandler  = new MagicalAttackHandler();

        physicalAttackHandler.setSuccessor(rangeCheckerHandler)
                             .setSuccessor(staminaCheckerHandler);

        magicalAttackHandler.setSuccessor(manaCheckerHandler);

        // Create fighters
        List<Fighter> fighters = new ArrayList<>();
        // Assign the designated chain to each character (? unsure)
        fighters.add(new Warrior(physicalAttackHandler));
        fighters.add(new Mage(magicalAttackHandler));

        // Simulate attacks
        System.out.println("Warrior is attacking :");
        fighters.get(0).attack(fighters.get(1)); // Warrior attacks Mage (should succeed)
        System.out.println("\nMage is attacking :");
        fighters.get(1).attack(fighters.get(0)); // Mage attacks Warrior (should succeed)
        System.out.println("\nMage is attacking :");
        fighters.get(1).attack(fighters.get(0)); // No more mana, should fail
    }
}

enum AttackType {
    PHYSICAL,
    MAGICAL
}

class AttackRequest {
    private final AttackType type;
    private final Fighter source;
    private final Fighter target;

    public AttackRequest(AttackType type, Fighter source, Fighter target) {
        this.type = type;
        this.source = source;
        this.target = target;
    }

    public AttackType getType() {
        return type;
    }

    public Fighter getSource() {
        return source;
    }

    public Fighter getTarget() {
        return target;
    }
}

abstract class AttackHandler {
    private AttackHandler successor;

    public AttackHandler setSuccessor(AttackHandler successor) {
        this.successor = successor;
        return successor;
    }

    public abstract boolean handleRequest(AttackRequest request);

    protected boolean invokeSuccessor(AttackRequest request) {
        if (successor != null) {
            return successor.handleRequest(request);
        }
        System.out.println("All handler passed!");
        return true; // End of the chain, attack succeeds
    }
}

class RangeCheckerHandler extends AttackHandler {
    @Override
    public boolean handleRequest(AttackRequest request) {
        // Check if the source and target are in range
        boolean inRange = true;

        if (inRange) {
            System.out.println("Range handler passed! ");
            return invokeSuccessor(request);
        } else {
            System.out.println("Target is out of range!");
            return false;
        }
    }
}

class StaminaCheckerHandler extends AttackHandler {
    @Override
    public boolean handleRequest(AttackRequest request) {
        Fighter source = request.getSource();
        if (source.getStamina() < 10) {
            System.out.println("Not enough stamina!");
            return false;
        }
        System.out.println("Stamina handler passed!");
        return invokeSuccessor(request);
    }
}

class ManaCheckerHandler extends AttackHandler {
    @Override
    public boolean handleRequest(AttackRequest request) {
        Fighter source = request.getSource();
        if (source.getMana() < 10) {
            System.out.println("Not enough mana!");
            return false;
        }
        System.out.println("Mana handler passed!");
        return invokeSuccessor(request);
    }
}

class PhysicalAttackHandler extends AttackHandler {
    @Override
    public boolean handleRequest(AttackRequest request) {
        if (request.getType() == AttackType.PHYSICAL) {
            System.out.println("Physical attack handler passed!");
            return invokeSuccessor(request);
        }
        return false;
    }
}

class MagicalAttackHandler extends AttackHandler {
    @Override
    public boolean handleRequest(AttackRequest request) {
        if (request.getType() == AttackType.MAGICAL) {
            System.out.println("Magical attack handler passed!");
            return invokeSuccessor(request);
        }
        return false;
    }
}


abstract class Fighter {
    private final AttackHandler handler;

    public Fighter(AttackHandler handler) {
        this.handler = handler;
    }

    public abstract int getMana();

    public abstract int getStamina();

    public void attack(Fighter target) {
        AttackRequest request = new AttackRequest(getAttackType(), this, target);
        if (!handler.handleRequest(request)) {
            System.out.println(this + " couldn't attack " + target);
        }
    }

    public String toString(){
        return getClass().getSimpleName();
    }

    protected abstract AttackType getAttackType();
}

class Warrior extends Fighter {

    private int stamina = 100;

    public Warrior(AttackHandler handler) {
        super(handler);
    }

    @Override
    public int getMana() {
        return 0; // Warriors don't have mana
    }

    @Override
    public int getStamina() {
        return stamina;
    }

    @Override
    protected AttackType getAttackType() {
        return AttackType.PHYSICAL;
    }
}

class Mage extends Fighter {

    private int mana = 100;

    public Mage(AttackHandler handler) {
        super(handler);
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public int getStamina() {
        return 0; // Mage don't have stamina
    }

    @Override
    protected AttackType getAttackType() {
        return AttackType.MAGICAL;
    }

    @Override
    public void attack(Fighter target) {
        super.attack(target);
        mana -= 100;
    }
}