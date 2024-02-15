async function newGame(url = "newGame") {
  // Default options are marked with *
  const response = await fetch("newGame", {
    method: "POST", // *GET, POST, PUT, DELETE, etc.
    credentials: "same-origin", // include, *same-origin, omit
    headers: {
      "Content-Type": "application/json",
      // 'Content-Type': 'application/x-www-form-urlencoded',
    }, // body data type must match "Content-Type" header
  });
  data = await response.json()
  document.getElementById('player-hand').innerHTML = "";
  document.getElementById('dealer-hand').innerHTML = "";
  document.getElementById('sessionId').value = data.sessionId;
  document.getElementById('player-score').innerHTML = "";
  document.getElementById('dealer-score').innerHTML = "";
  document.getElementById('winner').innerHTML = "";
  console.log("Your Session id is: " + data.sessionId)
  callPostAndUpdateGame("dealCards")
  return true; // parses JSON response into native JavaScript objects
}

function cardNameToPictureName(value,suit){
     suitName=suit[0]
     console.log(value+suit)
     switch(value) {
       case "Two":
         valueName="2";
         break;
       case "Three":
         valueName="3";
         break;
       case "Four":
         valueName="4";
         break;
       case "Five":
         valueName="5";
         break;
       case "Six":
         valueName="6";
         break;
       case "Seven":
         valueName="7";
         break;
       case "Eight":
         valueName="8";
         break;
       case "Nine":
         valueName="9";
         break;
       case "Ten":
         valueName="10";
         break;
       case "Jack":
         valueName="J";
         break;
       case "Queen":
         valueName="Q";
         break;
       case "King":
         valueName="K";
         break;
       case "Ace":
         valueName="A";
         break;
     }
     return valueName.concat(suitName);
}

function addCard(cardValue,cardSuit,location) {
        cardName=cardNameToPictureName(cardValue,cardSuit)
        let img = document.createElement("img");
        img.src ="cardPictures/" + cardName + ".jpg";
        document.getElementById(location).appendChild(img);
    }


async function hitCard() {
    callPostAndUpdateGame("blackJackHit")
}
async function callPostAndUpdateGame(url) {
    sessionId = document.getElementById('sessionId').value

    const response = await fetch(url, {
    method: "POST",
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
    },body: sessionId,
  });
  dataBack = await response.json();
  updateHand(dataBack.playerHand,dataBack.dealerHandShown)
  document.getElementById('sessionId').value = dataBack.sessionId;
}
function updateHand(playerHand,dealerHand) {
  document.getElementById('player-hand').innerHTML =""
  document.getElementById('dealer-hand').innerHTML =""

  playerHand.forEach((card) => {
  addCard(card.value,card.suit,'player-hand')
  }
  );
  dealerHand.forEach((card) => addCard(card.value,card.suit,'dealer-hand'));
}

async function endGame() {
    sessionId = document.getElementById('sessionId').value
    const response = await fetch("endGame", {
    method: "POST",
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
    },body: sessionId,
  });
  dataBack = await response.json();
  updateHand(dataBack.playerHand,dataBack.dealerHand)

  document.getElementById('player-score').innerHTML = "Player Final score was: " + dataBack.playerScore;
  document.getElementById('dealer-score').innerHTML = "Dealer Final score was: " + dataBack.dealerScore;
  document.getElementById('winner').innerHTML = "Did player win: " + dataBack.winner;
}

document.getElementById("new-game").addEventListener("click", newGame);
document.getElementById("hit-card").addEventListener("click", hitCard);
document.getElementById("end-game").addEventListener("click", endGame);
