package usa.devrocoding.synergy.assets.letters.object;

import lombok.Getter;

public enum Letter {

    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    I,
    j,
    K,
    L,
    M,
    N,
    O,
    P,
    Q,
    R,
    S,
    T,
    U,
    V,
    W,
    X,
    Y,
    Z;

    @Getter
    private String[] converted;

    Letter(String... converted){
        this.converted = converted;
    }

}
