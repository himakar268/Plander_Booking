package com.mdx.xyphose.usermgmt.service;

import com.mdx.xyphose.usermgmt.entity.Game;
import com.mdx.xyphose.usermgmt.entity.dto.GameDTO;
import com.mdx.xyphose.usermgmt.repo.GameRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameRepositry gameRepository;

    public List<GameDTO> getAllGames() {
        return gameRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public GameDTO getGameById(Long id) {
        return gameRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public GameDTO addGame(GameDTO gameDTO) {
        Game game = new Game();
        game.setName(gameDTO.getName());
        game.setImage(gameDTO.getImage());
        game.setMaxCapacity(gameDTO.getMaxCapacity());
        game.setAvailableCapacity(gameDTO.getAvailableCapacity());

        Game savedGame = gameRepository.save(game);
        return convertToDTO(savedGame);
    }

    public GameDTO updateGame(Long id, GameDTO gameDTO) {
        return gameRepository.findById(id).map(game -> {
            game.setName(gameDTO.getName());
            game.setImage(gameDTO.getImage());
            game.setMaxCapacity(gameDTO.getMaxCapacity());
            game.setAvailableCapacity(gameDTO.getAvailableCapacity());
            Game updatedGame = gameRepository.save(game);
            return convertToDTO(updatedGame);
        }).orElse(null);
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

    private GameDTO convertToDTO(Game game) {
        GameDTO dto = new GameDTO();
        dto.setId(game.getId());
        dto.setName(game.getName());
        dto.setImage(game.getImage());
        dto.setMaxCapacity(game.getMaxCapacity());
        dto.setAvailableCapacity(game.getAvailableCapacity());
        return dto;
    }
}
