package com.example.capstoneii.service;

import com.example.capstoneii.utils.datastructures.HashMap;

public class StopWordService {
    private final HashMap<String, Boolean> stopwords = new HashMap<>();

    private static final String[] STOPWORDS = new String[]{
            "a", "al", "algo", "algunas", "alguno", "algunos", "ante", "antes", "aquel", "aquella", "aquellas", "aquello", "aquellos",
            "aquí", "allí", "allá", "aún", "aunque", "bajo", "bastante", "bien", "cada", "casi", "como", "cómo", "con", "contigo", "contra",
            "cual", "cuáles", "cualquiera", "cuando", "cuanta", "cuantas", "cuanto", "cuantos", "de", "del", "desde", "después", "donde",
            "dos", "el", "él", "ella", "ellas", "ello", "ellos", "en", "entre", "era", "eran", "eras", "eres", "es", "esa", "esas", "ese", "eso",
            "esos", "esta", "estaba", "estaban", "estabas", "estamos", "están", "estar", "estas", "este", "esto", "estos", "estoy", "fue",
            "fuera", "fueron", "fui", "ha", "habían", "habías", "habíamos", "habiendo", "habla", "hablar", "haces", "hace", "hacen",
            "hacemos", "hacer", "hacia", "han", "has", "hasta", "hay", "haya", "he", "hemos", "hicieron", "hizo", "la", "las", "le", "les", "lo",
            "los", "me", "menos", "mi", "mis", "mismo", "mucha", "muchas", "mucho", "muchos", "muy", "nada", "ni", "ninguno", "ninguna",
            "ningunas", "ningunos", "no", "nos", "nosotras", "nosotros", "nuestra", "nuestras", "nuestro", "nuestros", "nunca", "o", "os",
            "otra", "otras", "otro", "otros", "para", "pero", "poco", "por", "porque", "pues", "qué", "que", "quien", "quienes", "se", "sea",
            "sean", "según", "ser", "si", "sí", "sido", "siempre", "siendo", "sin", "sobre", "sois", "solamente", "solo", "somos", "son",
            "soy", "su", "sus", "tal", "también", "tampoco", "tan", "tanto", "te", "ten", "tengo", "tenemos", "tenía", "tenían", "tendré",
            "tendrían", "ti", "tiene", "tienen", "toda", "todas", "todo", "todos", "tras", "tu", "tus", "tuvo", "un", "una", "unas", "uno",
            "unos", "usted", "ustedes", "va", "vamos", "van", "vayas", "verdad", "veces", "voy", "ya", "yo"
    };

    public StopWordService() {
        load();
    }

    private void load() {
        for (String word : STOPWORDS) {
            String key = normalize(word);
            if (!key.isEmpty()) {
                stopwords.add(key, Boolean.TRUE);
            }
        }
    }

    private String normalize(String s) {
        return s == null ? "" : s.toLowerCase().trim();
    }

    public boolean isStopword(String token) {
        if (token == null) return false;
        String key = normalize(token);
        return stopwords.get(key) != null;
    }
}
