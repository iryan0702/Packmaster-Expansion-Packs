package thePackmaster.cards.maridebuffpack;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.actions.maridebuffpack.GiveBlockAction;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.atb;


public class FacetiousFacade extends AbstractMariDebuffCard {
    public final static String ID = makeID("FacetiousFacade");

    public FacetiousFacade() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseBlock = this.block = 14;
        this.baseDamage = this.damage = 24;
        this.baseMagicNumber = this.baseDamage - this.baseBlock;
        this.tags.add(CardTags.STRIKE);
   }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainBlockAction(p, p, this.block));
        atb(new GiveBlockAction(p.currentBlock, this.block, m));
        atb(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        calculateDifference();
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        calculateDifference();
        this.initializeDescription();
    }

    public void calculateDifference(){
        this.baseMagicNumber = Math.max(0, this.baseDamage) - Math.max(0, this.baseBlock);
        int diff = this.damage - this.block; //calc effective damage

        this.isMagicNumberModified = diff != this.baseMagicNumber; //norm color if effective damage = base
        if(diff > 0){
            this.magicNumber = diff;
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        }else if(diff < 0){ //effectively gives block: red number always
            this.magicNumber = -diff;
            this.baseMagicNumber = this.magicNumber+1;
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1];
        }else{ //non
            this.magicNumber = 0;
            this.rawDescription = cardStrings.DESCRIPTION;
        }
    }

    @Override
    public void upp() {
        upgradeBlock(10);
        upgradeDamage(12);
    }
}


