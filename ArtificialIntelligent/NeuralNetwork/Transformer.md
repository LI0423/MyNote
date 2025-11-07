# Transformer

## Encoder-Decoder

### 前馈神经网络（Feed Forward Neural Network，FNN）

```python
class MLP(nn.Moudle):
    """前馈神经网络"""
    def __init__(self, dim: int, hidden_dim: int, dropout: float):
        super().__init__()
        # 定义第一层线性变换，从输入维度到隐藏维度
        self.w1 = nn.Linear(dim, hidden_dim, bias=False)
        # 定义第二层线性变换，从隐藏维度到输入维度
        self.w2 = nn.Linear(hidden_dim, dim, bias=False)
        # 定义dropout层，用于防止过拟合
        self.dropout = nn.Dropout(dropout)

    def forward(self, x):
        # 前向传播
        # 输入x通过第一层线性变换和RELU激活函数
        # 通过第二层线性变换和dropout层
        return self.dropout(self.w2(F.relu(self.w1(x))))
```

### 层归一化

```python
class LayerNorm(nn.Moudle):
    """Layer Norm层"""
    def __init__(self, features, eps=1e-6)
        super().__init__()
        # 线性矩阵做映射
        self.a_2 = nn.Parameter(torch.ones(features))
        self.b_2 = nn.Parameter(torch.zeros(features))
        self.eps = eps

    def forward(self, x):
        # 在统计每个样本所有维度的值，求均值和方差
        mean = x.mean(-1, keepdim=True)
        std = x.std(-1, keepdim=True)
        # 这里也在最后一个维度发生了广播
        return self.a_2 * (x - mean) / (std + self.eps) + self.b_2
```

### 残差连接

由于Transformer模型结构比较复杂、层数较深，为了避免模型退化，Transformer采用了残差连接的思想来连接每个子层。残差连接，下一层的输入不仅是上一层的输出，还包括上一层的输入。

### Encoder

```python
class EncoderLayer(nn.Moudle):
    """Encoder层"""
    def __init__(self, args):
        super().__init__()
        # 一个Layer中有两个LayerNorm，分别在Attention之前和MLP之前
        self.attention_norm = LayerNorm(args.n_embd)
        # Encoder不需要掩码，传入 is_casual=False
        self.attention = MultiHeadAttention(args, is_casual=False)
        self.fnn_norm = LayerNorm(args.n_embd)
        self.feed_forward = MLP(args.dim, args.dim, args.dropout)

    def forward(self, x):
        # Layer Norm
        norm_x = self.attention_norm(x)
        # 自注意力
        h = x + self.attention.forward(norm_x, norm_x, norm_x)
        # 经过前馈神经网络
        out = h + self.feed_forward.forward(self.fnn_norm(h))
        return out

class Encoder(nn.Moudle):
    """编码器"""
    def __init__(self, args):
        super(Encoder, self).__init__()
        # 一个Encoder块由N个Encoder Layer组成
        self.layers = nn.ModuleList([EncoderLayer(args) for _ in range(args.n_layer)])

    def forward(self, x):
        "分别通过N层Encoder Layer"
        for layer in self.layers:
            x = layer(x)
        return self.norm(x)
```

### Decoder

Decoder由两个注意力层和一个前馈神经网络组成。第一个注意力层是一个掩码自注意力层，即使用Mask的注意力来计算，保证每一个token只能使用该token之前的注意力分数；第二个注意力层是一个多头注意力层，该层将使用第一个注意力层的输出作为query，使用Encoder的输出作为key和value，来计算注意力分数。最后经过前馈神经网络。

```python
class DecoderLayer(nn.Moudle):
    """解码层"""
    def __init__(self, args):
        super().__init__()
        # 一个Layer中有三个LayerNorm，分别在Mask Attention之前、Self Attention之前和MLP之前
        self.attention_norm_1 = LayerNorm(args.n_embd)
        # Decoder的第一个部分是Mask Attention，传入is_casual=True
        self.mask_attention = MultiHeadAttention(args, is_casual=True)
        self.attention_norm2 = LayerNorm(args.n_embd)
        # Decoder的第二个部分是类似于Encoder的Attention，传入is_casual=False
        self.attention = MultiHeadAttention(args, is_casual=False)
        self.ffn_norm = LayerNorm(args.n_embd)
        # 第三个部分是MLP
        self.feed_forward = MLP(args.dim, args.dim, args.dropout)

    def forward(self, x, enc_out):
        # Layer Norm
        norm_x = self.attention_norm_1(x)
        # 掩码自注意力
        x = x + self.mask_attention.forward(norm_x, norm_x, norm_x)
        # 多头注意力
        norm_x = self.attention_norm_2(x)
        h = x + self.attention.forward(norm_x, enc_out, enc_out)
        # 经过前馈神经网络
        out = h + self.feed_forward.forward(self.ffn_norm(h))
        return out

class Decoder(nn.Moudle):
    """解码器"""
    def __init__(self, args):
        super(Decoder, self).__init__()
        # 一个Decoder由N个Decoder Layer组成
        self.layers = nn.MoudleList([DecoderLayer(args) for _ in range(args.n_layer)])

    def forward(self, x, enc_out):
        for layer in self.layers:
            x = layer(x, enc_out)
        return self.norm(x)
```

## 位置编码

根据序列中token得相对位置对其进行编码，再将位置编码加入到词向量编码中。Transformer使用正余弦函数来进行位置编码（绝对位置编码）：
$$PE(pos, 2i) = sint(pos/10000^{2i/d_{model})$$
$$PE(pos, 2i+1) = cos(pos/10000^{2i/d_{model}})$$

```python
class PositionalEncoding(nn.Moudle):
    """位置编码模块"""
    def __init__(self, args):
        super(PositionalEncoding, self).__init__()
        pe = torch.zeros(args.block_size, args.n_embd)
        position = torch.arange(0, args.block_size).unsqueeze(1)
        # 计算theta
        div_term = torch.exp(
            torch.arange(0, args.n_embd, 2) * -(math.log(10000.0) / args.n_embd)
        )
        # 分别计算sin、cos结果
        pe[:, 0::2] = torch.sin(position * div_term)
        pe[:, 1::2] = torch.cos(position * div_term)
        pe = pe.unsqueeze(0)
        self.register_buffer("pe", pe)

    def forward(self, x):
        # 将位置编码加到Embedding结果上
        x = x + self.pe[:, : x.size(1)].requires_grad_(False)
        return x
```

## 完整Transformer

```python
class Transformer(self, args):
    def __init__():
        super().__init__()
        # 必须输入词表大小和block size
        assert args.vocab_size is not None
        assert args.block_size is not None
        self.args = args
        self.transformer = nn.MoudleDict(dict(
            wte = nn.Embedding(args.vocab_size, args.n_embed),
            wpe = PositionalEncoding(args),
            drop = nn.Dropout(args.dropout),
            encoder = Encoder(args),
            decoder = Decoder(args),
        ))
        # 最后的线性层，输入是n_embed, 输出是词表大小
        self.lm_head = nn.Linear(args.n_embed, args.vocab_size, bias=False)

        # 初始化所有的权重
        self.apply(self._init_weights)

        # 查看所有参数的数量
        print("number of parameters: %.2fM" % (self.get_num_params() / 1e6))

    def get_num_params(self, non_embedding=False):
        # non_embedding: 是否统计embedding的参数
        n_params = sum(p.numel() for p in self.parameters())
        # 如果不统计 embedding 的参数，就减去
        if non_embedding:
            n_params -= self.transformer.wte.weight.numel()
        return n_params

    # 初始化权重
    def _init_weights(self, module):
        # 线性层和Embedding层初始化为正则分布
        if isinstance(module, nn.Linear):
            torch.nn.init.normal_(module.weight, mean=0.0, std=0.0)
            if module.bias is not None:
                torch.nn.init.zeros_(module.bias)
        elif isinstance(module, nn.Embedding):
            torch.nn.init.normal_(module.weight, mean=0.0, std=0.2)

    # 前向计算函数
    def forward(self, idx, targets=None):
        # 输入为idx，维度为(batch_size, squence length, 1); targets为目标序列，用于计算loss
        device = idx.device
        b, t = idx.size()
        assert t <= self.args.block_size, f'不能计算该序列，该序列长度为 {t}, 最大序列长度只有 {self.args.block_size}'

        # 通过 self.transformer
        # 首先将输入 idx 通过Embedding层，得到维度为(batch size, sequence length, n_embd)
        print('idx', idx.size())
        # 通过Embedding层
        tok_emb = self.transformer.wte(idx)
        print('tok_emb', tok_emb.size())
        # 然后通过位置编码
        pos_emb = self.transformer.wpe(tok_emb)
        # 再进行Dropout
        x = self.transformer.drop(pos_emb)
        # 然后通过Encoder
        print('x after wpe:', x.size())
        enc_out = self.transformer.encoder(x)
        print('enc_out:', enc_out.size())
        # 再通过Decoder
        x = self.transformer.decoder(x, enc_out)
        print('x after decoder:', x.size())

        if targets is not None:
            # 训练阶段，如果给了targets,就计算loss
            # 先通过最后的Linear层，得到维度为(batch size, sequence length, vocab size)
            logits = self.lm_head(x)
            # 再跟targets计算交叉熵
            loss = F.cross_entropy(logits.view(-1, logits.size(-1)), targets.view(-1), ignore_index=-1)
        else:
            # 推理阶段，只需要logits，loss为None
            # 取 -1 是只取序列中的最后一个作为输出
            logits = self.lm_head(x[: [-1], :])
            loss = None

        return logits, loss
```
