package com.paulzhangcc.sharing.message.assembly;

import com.paulzhangcc.sharing.SharingProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author: Paul Zhang
 * @date: 16:21 2017/12/27
 */
@Component
public class MessageContext implements MessageStrategy,InitializingBean{
    private MessageStrategy messageStrategy = null;

    @Autowired
    SharingProperties sharingProperties;
    public MessageContext(){
        this.messageStrategy = new AliMessageStrategy();
    }
    public MessageContext(MessageStrategy messageStrategy) {
        this.messageStrategy = messageStrategy;
    }

    @Override
    public boolean sendVerificationCode(String mobile, String code) {
        return messageStrategy.sendVerificationCode(mobile,code);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (sharingProperties.isOpenThirdpartyService()){
            this.messageStrategy=new AliMessageStrategy();
        }else {
            this.messageStrategy=new NoMessageStrategy();
        }
    }
}
