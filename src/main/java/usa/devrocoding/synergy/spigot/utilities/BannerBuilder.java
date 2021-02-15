package usa.devrocoding.synergy.spigot.utilities;

    import lombok.Getter;
    import org.bukkit.DyeColor;
    import org.bukkit.Material;
    import org.bukkit.block.Banner;
    import org.bukkit.block.Block;
    import org.bukkit.block.BlockState;
    import org.bukkit.block.banner.Pattern;
    import org.bukkit.block.banner.PatternType;
    import org.bukkit.inventory.ItemStack;
    import org.bukkit.inventory.meta.BannerMeta;
    import org.bukkit.inventory.meta.BlockStateMeta;

public class BannerBuilder extends ItemBuilder{

    @Getter
    private final BannerMeta bannerMeta;

    public BannerBuilder(){
        this(DyeColor.WHITE);
    }

    public BannerBuilder(DyeColor dyeColor){
        super(Material.BANNER);
        BannerMeta bannerMeta = (BannerMeta) getItemMeta();
        bannerMeta.setBaseColor(dyeColor);
        this.bannerMeta = bannerMeta;
        this.initItemMeta(bannerMeta);
    }

    public BannerBuilder addPattern(DyeColor color, PatternType type){
        this.bannerMeta.addPattern(new Pattern(color, type));
        return this;
    }

    public BannerBuilder setPattern(int place, DyeColor color, PatternType type){
        if (place <= 0)
            place = 1;

        if (this.bannerMeta.getPatterns().size() >= place) {
            this.bannerMeta.setPattern(place-1, new Pattern(color, type));
        }else{
            addPattern(color, type);
        }
        return this;
    }

    public static BannerBuilder getBannerColorByColor(DyeColor color){
        return new BannerBuilder(color);
    }

    public BlockState buildToBlockState(){
        return null;
    }

    // TODO: Find a workaround for this. Do not create new instances everytime.
    public ItemBuilder buildToShield(){
        ItemBuilder itemBuilder = new ItemBuilder(Material.SHIELD);
        BlockStateMeta blockStateMeta = (BlockStateMeta) itemBuilder.getItemMeta();

//        itemBuilder.initItemMeta(this.bannerMeta);
        Banner banner = (Banner) blockStateMeta.getBlockState();
        banner.setPatterns(this.bannerMeta.getPatterns());
        banner.update();

        blockStateMeta.setBlockState(banner);
        itemBuilder.initItemMeta(blockStateMeta);

        return itemBuilder;
    }

    public Block buildToBlock(){
        return null;
    }

    @Override
    public ItemStack build() {
        setItemMeta(this.bannerMeta);
        return super.build();
    }
}
