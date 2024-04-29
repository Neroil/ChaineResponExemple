package org.example.hihi;

import java.util.ArrayList;
import java.util.List;

public class main2 {
    public static void main(String[] args) {
        // Set up the chain of responsibility
        AttackHandler physicalAttackHandler = new PhysicalAttackHandler();
        AttackHandler rangeCheckerHandler = new RangeCheckerHandler();
        AttackHandler manaCheckerHandler = new ManaCheckerHandler();
        AttackHandler magicalAttackHandler = new MagicalAttackHandler();

        rangeCheckerHandler.setSuccessor(manaCheckerHandler);
        manaCheckerHandler.setSuccessor(physicalAttackHandler);
        physicalAttackHandler.setSuccessor(magicalAttackHandler);

        // Create fighters
        List<Fighter> fighters = new ArrayList<>();
        fighters.add(new Warrior(rangeCheckerHandler));
        fighters.add(new Mage(rangeCheckerHandler));

        // Simulate attacks
        fighters.get(0).attack(fighters.get(1)); // Warrior attacks Mage (should succeed)
        fighters.get(1).attack(fighters.get(0)); // Mage attacks Warrior (should succeed)
    }
}

enum AttackType {
    PHYSICAL,
    MAGICAL
}

class AttackRequest {
    private AttackType type;
    private Fighter source;
    private Fighter target;

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

    public void setSuccessor(AttackHandler successor) {
        this.successor = successor;
    }

    public abstract boolean handleRequest(AttackRequest request);

    protected boolean invokeSuccessor(AttackRequest request) {
        if (successor != null) {
            return successor.handleRequest(request);
        }
        return true; // End of the chain, attack succeeds
    }
}

class RangeCheckerHandler extends AttackHandler {
    @Override
    public boolean handleRequest(AttackRequest request) {
        // Check if the source and target are in range
        boolean inRange = true; // Implement your range check logic here

        if (inRange) {
            return invokeSuccessor(request);
        } else {
            System.out.println("Target is out of range!");
            return false;
        }
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
        return invokeSuccessor(request);
    }
}

class PhysicalAttackHandler extends AttackHandler {
    @Override
    public boolean handleRequest(AttackRequest request) {
        if (request.getType() == AttackType.PHYSICAL) {
            System.out.println("Physical attack succeeded!");
            return true;
        }
        return invokeSuccessor(request);
    }
}

class MagicalAttackHandler extends AttackHandler {
    @Override
    public boolean handleRequest(AttackRequest request) {
        if (request.getType() == AttackType.MAGICAL) {
            System.out.println("Magical attack succeeded!");
            return true;
        }
        return false; // End of the chain, attack fails
    }
}

abstract class Fighter {
    private AttackHandler handler;

    public Fighter(AttackHandler handler) {
        this.handler = handler;
    }

    public abstract int getMana();

    public void attack(Fighter target) {
        AttackRequest request = new AttackRequest(getAttackType(), this, target);
        if (!handler.handleRequest(request)) {
            System.out.println(this + " couldn't attack " + target);
        }
    }

    protected abstract AttackType getAttackType();
}

class Warrior extends Fighter {
    public Warrior(AttackHandler handler) {
        super(handler);
    }

    @Override
    public int getMana() {
        return 0; // Warriors don't have mana
    }

    @Override
    protected AttackType getAttackType() {
        return AttackType.PHYSICAL;
    }
}

class Mage extends Fighter {
    private final int MANA = 100;

    public Mage(AttackHandler handler) {
        super(handler);
    }

    @Override
    public int getMana() {
        return MANA;
    }

    @Override
    protected AttackType getAttackType() {
        return AttackType.MAGICAL;
    }
}