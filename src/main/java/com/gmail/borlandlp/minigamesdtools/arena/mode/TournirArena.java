package com.gmail.borlandlp.minigamesdtools.arena.mode;

public class TournirArena {
/*
    private LinkedList<Player> players;

    //TODO: добавить расчет нанесенного урона, если ничья?
    @Override
    public void beforeRoundStarting()
    {
        this.denyHealthRegen = false;
        ExampleTeam winner = this.getWinnerOfRoundTeam();
        if(winner != null) {
            for(ExampleTeam team : this.getTeams()) {
                if(!team.isSpectatorsTeam() && winner != team) {
                    team.preparePlayersAfterFight();
                    team.preparePlayersAfterGame();
                    for(Player player : team.getBukkitPlayers()) {
                        this.players.unregister(player);
                        player.sendMessage("defeat");
                    }

                } else if(!team.isSpectatorsTeam()) {
                    team.preparePlayersAfterGame();//tp out
                }
            }
        }

        if(this.players.size() <= 1) {
            this.gameWillBeEnd = true;
            return;
        }

        List<Player> players = new ArrayList<>();
        for(ExampleTeam team : this.getTeams()) {
            if(team.isSpectatorsTeam()) continue;

            team.removeAllPlayers();
            Player player = null;
            do {
                int rand = (int)(Math.random() * (this.players.size()));
                player = this.players.get((rand > 1) ? rand-1 : rand);
            } while(players.contains(player));
            players.add(player);
            team.addPlayer(player);
        }

        this.preparePlayersToGame();
    }

    @Override
    public void beforeGameStarting()
    {
        this.players = new LinkedList<Player>();
        for(ExampleTeam team : this.getTeams()) {
            for(Player player : team.getBukkitPlayers()) {
                if(player != null && player.isOnline()) {
                    this.players.add(player);
                }
            }

            if(team.isSpectatorsTeam()) team.doRespawnAndEquipPlayers();
        }
    }

    @Override
    public boolean checkRoundCustomConditions()
    {
        return (this.getAmountRemainSurvivedTeams() <= 1) ? false : true;
    }

    @Override
    public void update()
    {
        if(this.getTimeLeft() <= -60 && this.denyHealthRegen) {

            EntityDamageEvent event = null;
            for(ExampleTeam team : this.getTeams()) {
                if(team.isSpectatorsTeam()) continue;

                for(Player player : team.getBukkitPlayers()) {
                    event = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.MAGIC, 0.5D);
                    Bukkit.getPluginManager().callEvent(event);
                    //if(!event.isCancelled()) {
                        player.damage(event.getDamage());
                    //}
                }
            }

        } else if(this.getTimeLeft() <= 0) {
            if(!this.denyHealthRegen) {
                this.denyHealthRegen = true;
                this.broadcastMessage("Время для поединка истекло, в качестве штрафа вам отключено восстановление здоровья.",true);
                this.broadcastMessage("Через 60 секунд ваше здоровье начнет убавляться автоматически.",true);
            }
        }
    }

    @Override
    public boolean checkGameCustomConditions()
    {
        if(this.players.size() <= 1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int getAmountRemainSurvivedTeams()
    {
        int i = 0;
        for(ExampleTeam team : this.getTeams()) {
            if(team.isSpectatorsTeam()) continue;

            for(Player player : team.getBukkitPlayers()) {
                if(team.getDeaths() < team.getMaxDeaths() && player != null && player.isOnline()) {
                    i++;
                    continue;
                }
            }
        }

        return i;
    }

    @Override
    public void gameEnded()
    {
        this.removeAllPlayersFromCache();

        if(this.getState() == STATE.PAUSED) {
            return;
        }

        if(this.schedulerTask != null) this.schedulerTask.cancel();
        if(this.countdownTask != null) this.countdownTask.cancel();

        this.removePlayersScoreboards();

        ExampleTeam winners = this.getWinnerOfGameTeam();
        if(winners != null) {
            int amount = 0;
            for(Player player : winners.getBukkitPlayers()) {
                if(player == null || !player.isOnline()) {
                    continue;
                }

                for(ItemStack itemstack : this.getAwardItems()) {
                    player.getInventory().addItem(itemstack);
                }

                amount = (int)(this.getAwardEcons() + (this.econsBank / winners.getBukkitPlayers().size()));
                this.getPlugin().getEconomy().depositPlayer(player, amount);
            }
            winners.broadcastMessage("Вам была выдана награда в инвентарь + выигранные " + amount + " экон, с вычетом комиссии и выплатой награды от арены.");
            this.broadcastMessage(" Схватка завершилась победой команды " + winners.getName(), false);
        }

        this.removeAllPlayers();
        this.setState(STATE.EMPTY);
        try {
            this.resetConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Scoreboard getScoreboardManager() {
        ScoreboardWrapper sc_wrapper = new ScoreboardWrapper(ChatColor.GOLD + "★ PVP Турнир ★");
        for(ExampleTeam team : this.getTeams()) {
            if(team.isSpectatorsTeam()) {
                continue;
            }

            sc_wrapper.addLine("Команда " + team.getName());
            for(String nickname : team.getSourcePlayers().keySet()) {
                Player player = team.getSourcePlayers().get(nickname);
                if(player != null && player.isOnline()) {
                    String text = "- " + ChatColor.RED + player.getName() + ChatColor.WHITE + " => " + ChatColor.DARK_GREEN + "HP" + ChatColor.WHITE + ": " + ChatColor.DARK_GREEN + (int)player.getHealth();
                    sc_wrapper.addLine(text);
                } else {
                    sc_wrapper.addLine("- " + ChatColor.RED + nickname + ChatColor.WHITE + " => " + ChatColor.RED + "Не в сети");
                }
            }

        }
        sc_wrapper.addBlankSpace();
        sc_wrapper.addLine("Ост. времени: " + (this.getTimeLeft() < 0 ? ChatColor.RED + "" + Math.abs(this.getTimeLeft()) : this.getTimeLeft()));
        sc_wrapper.addLine(ChatColor.WHITE + "Ост. участников: " + this.players.size());

        return sc_wrapper.getScoreboard();
        //return this.scoreboardManager;
    }
*/
}
