package samuelesimeone.enumeratori;

public enum Periodo implements CharSequence {
    Settimanale,
    Mensile,
    Semestrale;

    @Override
    public int length() {
        return 0;
    }

    @Override
    public char charAt(int index) {
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }


}
