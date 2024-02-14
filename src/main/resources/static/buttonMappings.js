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
  return true; // parses JSON response into native JavaScript objects
}
async function dealCards() {
    callPostAndUpdateGame("dealCards")
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
  playerHandArray=[]
  playerHand.forEach((card) => playerHandArray.push(card.value + " Of " + card.suit));
  dealerHandArray=[]
  dealerHand.forEach((card) => dealerHandArray.push(card.value + " Of " + card.suit));
  playerHandString=playerHandArray.join(', ')
  dealerHandString=dealerHandArray.join(', ')
  document.getElementById('player-hand').innerHTML = playerHandString;
  document.getElementById('dealer-hand').innerHTML = dealerHandString;
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
document.getElementById("deal-cards").addEventListener("click", dealCards);
document.getElementById("hit-card").addEventListener("click", hitCard);
document.getElementById("end-game").addEventListener("click", endGame);
