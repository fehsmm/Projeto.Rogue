package pt.upskill.projeto1.objects.enemy;


public enum EnemyType { //no ImageTitle vai chamar pelo nome e no Room pelo char
    SKELETON("Skeleton", 'S', 25, 15),
    BAT("Bat", 'B', 30, 8),
    BADGUY("BadGuy", 'G', 50,20),
    THIEF("Thief", 'T', 70, 30);

    private final String imageName;
    private final char symbol;
    private int maxHealth;
    private int damage;

    private EnemyType(String imageName, char symbol,int maxHealth,int damage){ //No Enum pode ter construtor desde q seja privado.
        this.imageName = imageName;
        this.symbol = symbol;
        this.maxHealth = maxHealth;
        this.damage = damage;
    }

    public String getImageName() {
        return imageName;
    }
    public char getSymbol() {
        return symbol;
    }
    public int getMaxHealth(){
        return maxHealth;
    }
    public int getDamage() {
        return damage;
    }

    public static EnemyType fromSymbol(char c) {
        for (EnemyType type : values()) {
            if (type.symbol == c)
                return type;
        }
        return null;
    }

}
/*
Os adversários movimentam-se aleatoriamente quando estão “longe” do herói, e convergem para o herói
quando estão “perto”.
Thief - Esta personagem deve ter uma movimentação especifica sendo semelhante à peça de xadrez do bispo,
ou seja, a sua movimentação deve ser feita apenas na diagonal.
 */
