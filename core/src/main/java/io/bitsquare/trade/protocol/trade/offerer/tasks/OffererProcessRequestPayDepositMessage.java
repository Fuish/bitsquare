/*
 * This file is part of Bitsquare.
 *
 * Bitsquare is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bitsquare is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bitsquare. If not, see <http://www.gnu.org/licenses/>.
 */

package io.bitsquare.trade.protocol.trade.offerer.tasks;

import io.bitsquare.common.taskrunner.TaskRunner;
import io.bitsquare.trade.OffererTrade;
import io.bitsquare.trade.protocol.trade.messages.RequestPayDepositMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.*;
import static io.bitsquare.util.Validator.*;

public class OffererProcessRequestPayDepositMessage extends OffererTradeTask {
    private static final Logger log = LoggerFactory.getLogger(OffererProcessRequestPayDepositMessage.class);

    public OffererProcessRequestPayDepositMessage(TaskRunner taskHandler, OffererTrade model) {
        super(taskHandler, model);
    }

    @Override
    protected void doRun() {
        try {
            RequestPayDepositMessage message = (RequestPayDepositMessage) offererTradeProcessModel.getTradeMessage();
            checkTradeId(offererTradeProcessModel.getId(), message);
            checkNotNull(message);

            offererTradeProcessModel.taker.setConnectedOutputsForAllInputs(checkNotNull(message.buyerConnectedOutputsForAllInputs));
            checkArgument(message.buyerConnectedOutputsForAllInputs.size() > 0);
            offererTradeProcessModel.taker.setOutputs(checkNotNull(message.buyerOutputs));
            offererTradeProcessModel.taker.setTradeWalletPubKey(checkNotNull(message.buyerTradeWalletPubKey));
            offererTradeProcessModel.taker.setP2pSigPubKey(checkNotNull(message.buyerP2PSigPublicKey));
            offererTradeProcessModel.taker.setP2pEncryptPubKey(checkNotNull(message.buyerP2PEncryptPublicKey));
            offererTradeProcessModel.taker.setFiatAccount(checkNotNull(message.buyerFiatAccount));
            offererTradeProcessModel.taker.setAccountId(nonEmptyStringOf(message.buyerAccountId));

            complete();
        } catch (Throwable t) {
            t.printStackTrace();
            offererTrade.setThrowable(t);
            failed(t);
        }
    }
}