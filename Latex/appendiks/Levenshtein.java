package Algoritme;

class Levenshtein {

    public final double distance(final String s1, final String s2) {
        return distance(s1, s2, Integer.MAX_VALUE);
    }

    /*
     * @param s1 Den foerste string som sammenlignes 
     * @param s2 Den anden string som sammenlignes 
     * @param limit Det maksimale resultat der beregnes foer stop. 
     * @return Den beregnede Levenshtein afstand.
     * @throws NullPointerException hvis s1 eller s2 er null.
     */

    public final double distance(final String s1, final String s2,
            final int limit) {

        if (s1.equals(s2)) {
            return 0;
        }

        if (s1.length() == 0) {
            return s2.length();
        }

        if (s2.length() == 0) {
            return s1.length();
        }

        // opretter to vector af heltal
        int[] v0 = new int[s2.length() + 1];
        int[] v1 = new int[s2.length() + 1];
        int[] vtemp;

        // initialisere v0 (den tidligere raekkedistance) 
        // Denne raekke er A[0][i]: Rediger distance for en tomt string 
        // Distancen er antallet af karakterer som slettes fra t
        for (int i = 0; i < v0.length; i++) {
            v0[i] = i;
        }

        for (int i = 0; i < s1.length(); i++) {
            // Beregner v1 (nuvaerende raekkedistance) fra den tidligere raekke v0
            // Foerste element af v1 er A[i+1][0]
            // Redigeringsdistancen er slettet (i+1) karakter fra s til at matche tomt t 
            v1[0] = i + 1;

            int minv1 = v1[0];

            // anvender formel til at fylde resten af raekken
            for (int j = 0; j < s2.length(); j++) {
                int cost = 1;
                if (s1.charAt(i) == s2.charAt(j)) {
                    cost = 0;
                }
                v1[j + 1] = Math.min(
                        v1[j] + 1, // Antallet af tilfoejede
                        Math.min(
                                v0[j + 1] + 1, // Antallet af slettede
                                v0[j] + cost)); // Antallet af erstattede

                minv1 = Math.min(minv1, v1[j + 1]);
            }

            if (minv1 >= limit) {
                return limit;
            }

            // Kopiere v1 (nuvaerende raekke) til v0 (tidligere raekke) for naeste 			iteration
            // Flipper referencer til nuvaerende og forrige raekke
            vtemp = v0;
            v0 = v1;
            v1 = vtemp;

        }

        return v0[s2.length()];
    }
}