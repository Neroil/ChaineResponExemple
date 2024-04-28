package org.example.hihi;

public class main2 {
    public static void main(String[] args) {
        AttackHandler physicalAttackHandler = new PhysicalAttackHandler(null);
        AttackHandler magicalAttackHandler = new MagicalAttackHandler(physicalAttackHandler);

        Person[] fightingRing = new Person[3];

        Person gimli = new Person(physicalAttackHandler);
        Mage saruman = new Mage(magicalAttackHandler);

        fightingRing[0] = gimli;
        fightingRing[2] = saruman;

        gimli.attack(saruman); // Physical attack request, will be handled
        saruman.attack(gimli); // Magical attack request, will be handled

        fightingRing[1] = gimli;
    }
}

enum Kind {
    MagicalAttack,
    PhysicalAttack
}

class Request {
    private Kind requestKind;

    public Request(Kind requestKind) {
        this.requestKind = requestKind;
    }

    public Kind getKind() {
        return requestKind;
    }
}

abstract class AttackHandler {
    private AttackHandler successor;

    public AttackHandler(AttackHandler successor) {
        this.successor = successor;
    }

    public void handleAttackRequest(Request request) {
        if (canHandleRequest(request)) {
            handleRequest(request);
        } else if (successor != null) {
            successor.handleAttackRequest(request);
        } else {
            // No handler available for the request
        }
    }

    protected abstract boolean canHandleRequest(Request request);
    protected abstract void handleRequest(Request request);
}

class PhysicalAttackHandler extends AttackHandler {
    public PhysicalAttackHandler(AttackHandler successor) {
        super(successor);
    }

    @Override
    protected boolean canHandleRequest(Request request) {
        return request.getKind() == Kind.PhysicalAttack;
    }

    @Override
    protected void handleRequest(Request request) {
        // Handle the physical attack request here
        System.out.println("Handling physical attack request");
    }
}

class MagicalAttackHandler extends AttackHandler {
    public MagicalAttackHandler(AttackHandler successor) {
        super(successor);
    }

    @Override
    protected boolean canHandleRequest(Request request) {
        return request.getKind() == Kind.MagicalAttack;
    }

    @Override
    protected void handleRequest(Request request) {
        // Handle the magical attack request here
        System.out.println("Handling magical attack request");
    }
}

class Person {
    protected AttackHandler handler;

    public Person(AttackHandler handler) {
        this.handler = handler;
    }

    public int getMana() {
        return 0;
    }

    public void attack(Person p) {
        Request request = new Request(Kind.PhysicalAttack);
        handler.handleAttackRequest(request);
    }
}

class Mage extends Person {
    public Mage(AttackHandler handler) {
        super(handler);
    }

    @Override
    public int getMana() {
        return 100;
    }

    @Override
    public void attack(Person p) {
        Request request = new Request(Kind.MagicalAttack);
        handler.handleAttackRequest(request);
    }
}