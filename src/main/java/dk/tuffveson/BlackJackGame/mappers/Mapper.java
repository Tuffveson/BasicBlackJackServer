package dk.tuffveson.BlackJackGame.mappers;


public interface Mapper<Destination,Source> {
    Destination sourceToDestination(Source source);
    Source destinationToSource(Destination destination);
}