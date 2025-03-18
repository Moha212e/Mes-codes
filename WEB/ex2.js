function arabic(roman) {
    const romanToArabic = {
        "I": 1,
        "V": 5,
        "X": 10,
        "L": 50,
        "C": 100,
        "D": 500,
        "M": 1000
    };

    let total = 0;
    let prevValue = 0;

    for (let i = roman.length - 1; i >= 0; i--) {
        const currentValue = romanToArabic[roman[i]];

        if (currentValue < prevValue) {
            total -= currentValue;
        } else {
            total += currentValue;
        }

        prevValue = currentValue;
    }

    return total;
}

// Exemple d'utilisation
console.log(arabic("CDLXXXIII")); // Doit retourner 483
console.log(arabic("IV")); // Doit retourner 4
console.log(arabic("IX")); // Doit retourner 9
console.log(arabic("XII")); // Doit retourner 12
console.log(arabic("MMXX")); // Doit retourner 2020