
public class Star {
    private StarType type;
    private int ageYears;
    private int massTons;

    public Star(StarType type, int ageYear, int massTons) {
        this.type = type;
        this.ageYears = ageYear;
        this.massTons = massTons;
    }

    public void timePasses(){
        ageYears *= 2;
        massTons *= 8;
        switch (type) {
            case RED_GIANT -> type = StarType.WHITE_DWARF;
            case SUN -> type = StarType.RED_GIANT;
            case SUPERNOVA -> type = StarType.DEAD;
            case WHITE_DWARF -> type = StarType.SUPERNOVA;        
            case DEAD -> {
                ageYears *= 2;
                massTons = 0;
            }
            default -> {
                
            }
        }
    }

    StarMemento getMemento(){
        StarMementoInternal starMementoInternal = new StarMementoInternal();
        starMementoInternal.setAgeYears(ageYears);
        starMementoInternal.setMassTons(massTons);
        starMementoInternal.setType(type);
        return starMementoInternal;
    }

    void setMemento(StarMemento memento){
        StarMementoInternal starMementoInternal = (StarMementoInternal) memento;
        this.type = starMementoInternal.getType();
        this.ageYears = starMementoInternal.getAgeYears();
        this.massTons = starMementoInternal.getMassTons();
    }

    @Override
    public String toString() {
        return String.format("%s age: %d years mass: %d tons", type.toString(), ageYears, massTons);
    }

    private static class StarMementoInternal implements StarMemento{
    
        private StarType type;
        private int ageYears;
        private int massTons;
        public StarType getType() {
            return type;
        }
        public void setType(StarType type) {
            this.type = type;
        }
        public int getAgeYears() {
            return ageYears;
        }
        public void setAgeYears(int ageYears) {
            this.ageYears = ageYears;
        }
        public int getMassTons() {
            return massTons;
        }
        public void setMassTons(int massTons) {
            this.massTons = massTons;
        }

        
    }
    
}
