// Maga a gomb ami meghívja
const btn = document.getElementsByName("asd");
const player3 = document.getElementById("player3");
const player4 = document.getElementById("player4");
const Iplayer3 = document.getElementById("Iplayer3");
const Iplayer4 = document.getElementById("Iplayer4");
const Tplayer3 = document.getElementById("Tplayer3");
const Tplayer4 = document.getElementById("Tplayer4");

function connectPlayer(){
    if(player3.classList == "kHely" && player4.classList == "kHely"){
        // 3. játékos eltávolítása
        player3.classList = "";
        Iplayer3.classList = "";
        Tplayer3.classList = "";
        Tplayer3.innerHTML = ""
        // 4. játékos eltávolítása
        player4.classList = "";
        Iplayer4.classList = "";
        Tplayer4.classList = "";
        Tplayer4.innerHTML = ""
    }else{
        if(player3.classList == ""){
            // 3. játékos hozzáadása
            player3.classList = "kHely";
            Iplayer3.classList = "kInfo"
            Tplayer3.classList = "texts"
            Tplayer3.innerHTML = "Ellenfél paklija"
        }else{
            // 4. játékos hozzáadása
            player4.classList = "kHely";
            Iplayer4.classList = "kInfo"
            Tplayer4.classList = "texts"
            Tplayer4.innerHTML = "Ellenfél paklija"
        }
    }
}