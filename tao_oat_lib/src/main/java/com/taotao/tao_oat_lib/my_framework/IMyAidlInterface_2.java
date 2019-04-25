package com.taotao.tao_oat_lib.my_framework;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * @package com.taotao.tao_oat_lib.my_framework
 * @file IMyAidlInterface_2
 * @date 2019/4/25  5:53 PM
 * @autor wangxiongfeng
 *
 *
 *AIDL 是java 的一个功能用来自动生成  实际就是更具一个接口文件（服务端所能提供的 功能） 生成
 *
 * 1。接口
 * 2。stub 类（存根）
 * 3。prox 类（代理）
 *
 *
 */
public interface IMyAidlInterface_2 extends IInterface {

    void basicTypes(int var1, long var2, boolean var4, float var5, double var6, String var8) throws RemoteException;

    public abstract static class Stub extends Binder implements IMyAidlInterface {
        private static final String DESCRIPTOR = "com.taotao.tao_oat_lib.my_framework.IMyAidlInterface";
        static final int TRANSACTION_basicTypes = 1;

        public Stub() {
            this.attachInterface(this, "com.taotao.tao_oat_lib.my_framework.IMyAidlInterface");
        }

        public static IMyAidlInterface asInterface(IBinder obj) { 
            if (obj == null) {
                return null;
            } else {
                IInterface iin = obj.queryLocalInterface("com.taotao.tao_oat_lib.my_framework.IMyAidlInterface");
                return (IMyAidlInterface)(iin != null && iin instanceof IMyAidlInterface ? (IMyAidlInterface)iin : new IMyAidlInterface_2.Stub.Proxy(obj));
            }
        }

        public IBinder asBinder() {
            return this;
        }

        /**
         *
         * 服务端接受到数据后 根据code 的值 调用对应的函数 对传过来的参数进行 处理。
         * @param code 更具code调用 对应对函数
         * @param data
         * @param reply
         * @param flags
         * @return
         * @throws RemoteException
         */
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {

            //服务端 解析数据 然后 调用服务提供端
            switch(code) {
                case 1:
                    data.enforceInterface("com.taotao.tao_oat_lib.my_framework.IMyAidlInterface");
                    int _arg0 = data.readInt();
                    long _arg1 = data.readLong();
                    boolean _arg2 = 0 != data.readInt();
                    float _arg3 = data.readFloat();
                    double _arg4 = data.readDouble();
                    String _arg5 = data.readString();
                    this.basicTypes(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.taotao.tao_oat_lib.my_framework.IMyAidlInterface");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMyAidlInterface {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "com.taotao.tao_oat_lib.my_framework.IMyAidlInterface";
            }

            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
               // 客户端对数据进行包装 然后发送给server code 值就是要调用server 端的方法编号

                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();

                try {
                    _data.writeInterfaceToken("com.taotao.tao_oat_lib.my_framework.IMyAidlInterface");
                    _data.writeInt(anInt);
                    _data.writeLong(aLong);
                    _data.writeInt(aBoolean ? 1 : 0);
                    _data.writeFloat(aFloat);
                    _data.writeDouble(aDouble);
                    _data.writeString(aString);

                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }

            }
        }
    }
}
