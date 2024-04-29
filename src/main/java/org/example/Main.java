//package org.example;
//
//public class Main {
//    public static void main(String[] args) {
//        AttackHandler physicalAttackHandler = new PhysicalAttackHandler();
//        AttackHandler magicalAttackHandler = new MagicalAttackHandler(physicalAttackHandler);
//
//        Person[] fightingRing = new Person[3];
//
//        Person Gimli = new Person(physicalAttackHandler);
//        Mage Saruman = new Mage(magicalAttackHandler);
//
//        fightingRing[0] = Gimli;
//        fightingRing[2] = Saruman;
//
//        Gimli.attack(Saruman); //PhysicalAttack request, will fail
//        Saruman.attack(Gimli); //MagicalAttack request
//
//
//        fightingRing[1] = Gimli;
//    }
//}
//
//enum Kind {
//    MagicalAttack,
//    PhysicalAttack
//}
//
//class Request {
//
//    private Kind requestKind;
//
//    public Request(Kind resquestKind){
//        this.requestKind = resquestKind;
//    }
//
//    public Kind getKind() {
//        return requestKind;
//    }
//}
//
//class AttackRequest {
//    private Person p;
//
//    public int getMana(){
//        return p.getMana();
//    }
//
//
//
//    RangeCheckerHandler r = new RangeCheckerHandler()
//            .setSuccessor(new ManaCheckerHandler())
//            .setSuccessor(new TypeAttackHandler());
//
//    if(r.handleAttackRequest(request)){
//        //attaque reussie
//    }else{
//        //attaque ratée
//    }
//
//
//}
//
//
//abstract class AttackHandler {
//    protected AttackHandler _successor;
//
//    public AttackHandler(){
//        this._successor = null;
//    }
//
//    public AttackHandler setSuccessor(AttackHandler _successor){
//        this._successor = _successor;
//        return _successor;
//    }
//
//    public boolean handleAttackRequest(Request request){
//        if(this._successor != null) {
//            return this._successor.handleAttackRequest(request);
//        }
//        return true; //If there was nothing stopping us from attacking :p
//    }
//}
//
//class PhysicalAttackHandler extends AttackHandler {
//
//    public PhysicalAttackHandler(){
//        super();
//    }
//
//    public PhysicalAttackHandler(){
//        super();
//    }
//
//    @Override
//    public boolean handleAttackRequest() {
//        if (request.getKind() == Kind.PhysicalAttack) {
//            // Handle the request here
//        } else super.handleAttackRequest();
//    }
//}
//
//class RangeCheckerHandler extends AttackHandler{
//
//    public boolean rangeValid(Request request){
//        // Check range
//        return true;
//    }
//
//    public RangeCheckerHandler() {
//        super();
//    }
//
//    @Override
//    public boolean handleAttackRequest(Request request){
//        if(rangeValid(request)){
//            return super.handleAttackRequest(request);
//        }
//        return false;
//    }
//}
//
//class ManaCheckerHandler extends AttackHandler{
//
//
//    public boolean haveEnoughMana(Request request){
//        // Check if have enough mana
//        return true;
//    }
//
//    public ManaCheckerHandler() {
//        super();
//    }
//
//    @Override
//    public boolean handleAttackRequest(Request request){
//        if(haveEnoughMana(request)){
//            return super.handleAttackRequest(request);
//        }
//        return false;
//    }
//}
//
//
//class MagicalAttackHandler extends AttackHandler {
//
//    public MagicalAttackHandler(AttackHandler _successor){
//        super(_successor);
//    }
//
//    public MagicalAttackHandler(){
//        super();
//    }
//
//    @Override
//    public void handleAttackRequest(Request request) {
//        switch(request.getKind()){
//            case MagicalAttack:
//                // Send to Magical Attack Handler
//
//                break;
//            case PhysicalAttack:
//                // Send to Physical Attack Handler
//                break;
//            default:
//                super.handleAttackRequest(request);
//        }
//    }
//}
//
//class Person {
//    protected AttackHandler handler;
//
//    public Person(AttackHandler handler) {
//        this.handler = handler;
//    }
//
//    public int getMana() {
//        return 0;
//    }
//
//    public void attack(Person p){
//        if (!handler.handleAttackRequest(getRequest())){
//            System.out.println(this + " couldn't attack :(");
//        }
//    }
//
//    private Request getRequest(){
//        return new Request(Kind.PhysicalAttack);
//    }
//}
//
//class Mage extends Person{
//    public Mage(AttackHandler handler) {
//        super(handler);
//    }
//
//    @Override
//    public int getMana() {
//        return 100;
//    }
//
//    @Override
//    private Request getRequest(){
//        return new Request(Kind.MagicalAttack);
//    }
//}
