function reverse(str) {
    if (str === "") {
        return "";
    } else {
        return reverse(str.substr(1)) + str.charAt(0);
    }
}

// Exemple d'utilisation
console.log(reverse('live')); // Doit retourner 'evil'